package perso.free.time.userManagement.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Jwt;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.repository.JwtRepository;
import perso.free.time.userManagement.service.UtilisateurService;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {

    public static final String BEARER = "bearer";
    //https://randomgenerate.io/encryption-key-generator le site pour générer un clé
    private final String ENCRYPTION_KEY =  "1023a13b8e93ed66f7ce9ac527a1cab843c79993ab153f5f1f9425b5b63bb5be";
    private UtilisateurService utilisateurService;
    private JwtRepository jwtRepository;

    public Jwt tokenByValeur(String value) {
        return this.jwtRepository.findByValeur(value)
                .orElseThrow(()-> new RuntimeException("token inconnu"));
    }

    public Map<String, String> generate(String username){
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
        Map<String, String> jwtMap = this.generateJwt(utilisateur);
        final Jwt jwt = Jwt
                .builder().
                valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .utilisateur(utilisateur)
                .build();
        jwtRepository.save(jwt);
        return jwtMap;

    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }
    public boolean isTokenExpired(String token){

        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }
    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.getkey()).
                build().parseClaimsJws(token).getBody();
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime+(30*60*1000);
        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                "role", utilisateur.getRole(),
                Claims.SUBJECT ,utilisateur.getEmail(),
                Claims.EXPIRATION,new Date(expirationTime)
        );


        final String bearer = Jwts.builder().
                setIssuedAt(new Date(currentTime)).
                setExpiration(new Date(expirationTime)).
                setSubject(utilisateur.getEmail()).
                setClaims(claims).signWith(getkey(), SignatureAlgorithm.HS256).compact();
    return Map.of(BEARER,bearer);
    }

    private Key getkey() {
        byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }



}
