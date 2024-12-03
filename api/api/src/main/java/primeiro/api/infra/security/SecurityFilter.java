package primeiro.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import primeiro.api.usuario.repository.UsuarioRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubjetc(tokenJWT);
            var usuario = usuarioRepository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("logado com sucesso");
        }


        filterChain.doFilter(request, response);


    }

    private String recuperarToken(HttpServletRequest request) {
        var autjorizationHeader = request.getHeader("Authorization");
        if (autjorizationHeader != null) {
            return autjorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
