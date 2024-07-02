package alessiovulpinari.u2_w3_d2.security;

import alessiovulpinari.u2_w3_d2.entities.Employee;
import alessiovulpinari.u2_w3_d2.exceptions.UnathorizedException;
import alessiovulpinari.u2_w3_d2.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Prendiamo header Authorization
        String authHeader = request.getHeader("Authorization");

        // Controlliamo se è nullo o se inizia con Bearer (in caso contrario lanciamo un eccezione 401)
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnathorizedException("Per favore inserisci correttamente il token nell'header");

        // Prendiamo il token dopo la parola Bearer
        String accessToken = authHeader.substring(7);

        // verifichiamo la validità del Token
        jwtTools.verifyToken(accessToken);

        // Estraiamo l'id dal token
        String employeeId = jwtTools.extractIdFromToken(accessToken);

        // Cerchiamo l'id del dipendente con il suo service
        Employee currentEmployee = employeeService.findById(UUID.fromString(employeeId));

        // Trovato il dipendente lo aggiungiamo al security context questo equivale ad 'associare' il dipendente autenticato alla richiesta corrente
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, currentEmployee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Passiamo al prossimo check nella lista dei filtri
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Uso questo metodo per specificare in quali situazioni NON USARE I FILTRI (QUELLI DI SOPRA)
        // Posso ad esempio escludere dal controllo del filtro tutti gli endpoint dentro il controller /api/auth
        return new AntPathMatcher().match("/api/auth/**", request.getServletPath());
    }
}
