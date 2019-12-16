package com.xincan.transaction.oauth.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Strings;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.security.Principal;
import java.util.Map;

public class TokenUtils {

    // token 分隔符
    private static final char SEPARATOR_CHAR = '.';

    /**
     * 根据签名解析token
     * @param principal
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Claims decodeToken(Principal principal) throws Exception {
        if (principal == null) {
            return null;
        }
        // 从principal中获取jwt的token
        String jwt = ((OAuth2AuthenticationDetails)((OAuth2Authentication) principal).getDetails()).getTokenValue();
        // token头部
        String base64UrlEncodedHeader = null;
        // token payload
        String base64UrlEncodedPayload = null;
        // token 加密部分
        String base64UrlEncodedDigest = null;

        ObjectMapper objectMapper = new ObjectMapper();
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        // 按分隔符分隔token
        for (char c : jwt.toCharArray()) {
            if (c == SEPARATOR_CHAR) {
                CharSequence tokenSeq = Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;
                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }

        // =============== 解析Header部分 =================
        Header header = null;
        CompressionCodec compressionCodec = null;

        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = objectMapper.readValue(origValue, Map.class);

            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = (new DefaultCompressionCodecResolver()).resolveCompressionCodec(header);
        }

        // =============== 解析Body部分 =================
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        Claims claims = null;
        if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') {
            Map<String, Object> claimsMap = objectMapper.readValue(payload, Map.class);
            claims = new DefaultClaims(claimsMap);
        }

        return claims;
    }

}
