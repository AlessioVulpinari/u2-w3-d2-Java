package alessiovulpinari.u2_w3_d2.security;

import alessiovulpinari.u2_w3_d2.entities.Employee;
import alessiovulpinari.u2_w3_d2.exceptions.UnathorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di creazione token in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // Data di scadenza token in millisecondi
                .subject(String.valueOf(employee.getEmployeeId())) // Il soggetto (meglio passare l'id e non altri dati)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Creazione della Signature grazie al body e al segreto
                .compact();
    }

    public void verifyToken(String token){ // Dato un token verificami se è valido
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
            // Il metodo .parse(token) mi lancerà varie eccezioni in caso di token scaduto o malformato o manipolato

        } catch (Exception ex){
            throw new UnathorizedException("Problemi col token! Per favore effettua di nuovo il login!");
            // Non importa se l'eccezione lanciata da .parse() sia un'eccezione perché il token è scaduto o malformato o manipolato, a noi interessa solo che il risultato sia un 401
        }
    }

    // Funzione per estrarre l'Id da un Token
    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject(); // Questo è l'id dell'utente
    }

}
