package com.bcaf.tama.FinalProject.Util;

import com.bcaf.tama.FinalProject.Entity.Agency;
import com.bcaf.tama.FinalProject.Entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateJWT {

    public String buildJWT(User user, String agencyId){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("1a899507b21edd08f4ad03bfacd5cce2aba853cc7e7f4cd08808d8be53ade680");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map dataUser = new HashMap<String, Object>();
        dataUser.put("userId", user.getId());
        dataUser.put("name", user.getFirstName() + " " + user.getFirstName());
        dataUser.put("email", user.getEmail());
        dataUser.put("agencyId", agencyId);

        JwtBuilder builder = Jwts.builder()
                .setClaims(dataUser)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + 315536000000L;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);


        return builder.compact();
    }


    public String buildJWT(String id,String issuer,String subject,long tlMillis){
        SignatureAlgorithm signatureAlgorithm= SignatureAlgorithm.HS256;
        long nowMillis=System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes= DatatypeConverter.parseBase64Binary("e53062bedfee842f17ea60e10da1268e515ae54f7207daa501d6bd18263fbe2ec91ce12e52ef7e79573a4e7bc00450902f37c4ecc0299ed32e8223b9e1de3481");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm,signingKey);
        if(tlMillis>=0){
            long expMillis=nowMillis-tlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


}
