package br.com.hiago640.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import br.com.hiago640.todolist.model.User;
import br.com.hiago640.todolist.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TaskAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
		if (!servletPath.startsWith("/tasks")) {
			filterChain.doFilter(request, response);
		} else {

			// pegar autenticacao

			String authorization = request.getHeader("Authorization");
			System.out.println("Authorization: " + authorization);

			// separar o basic do header
			String authEncoded = authorization.substring("Basic".length()).trim();
			System.out.println(authEncoded);

			byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
			System.out.println(authDecoded);


			System.out.println("authDecoded" + authDecoded);

			String[] credentials = new String(authDecoded).split(":");
			System.out.println("credentials" + credentials + " size: " + credentials.length );
			//pega username e senha
			String username = credentials[0];
			String password = credentials[1];

			User user = userRepository.findByUsername(username);

			System.out.println(user);
			//procura usuario no sistema pelo username

			if (user == null) {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "User sem autorização");
			} else {

				Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

				//se senha for igual, segue, se não, dispara erro
				if (passwordVerify.verified) {
					UUID idUser = user.getId();
					//setta o idUser no request

					request.setAttribute("idUser", idUser);
					System.out.println(request.getAttribute("idUser"));

					filterChain.doFilter(request, response);
				} else {
					response.sendError(HttpStatus.UNAUTHORIZED.value(), "User sem autorização");
				}

			}

		}
	}

}
