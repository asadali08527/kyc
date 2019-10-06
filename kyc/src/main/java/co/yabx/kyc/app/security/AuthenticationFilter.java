package co.yabx.kyc.app.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	AuthenticationFilter(final RequestMatcher requiresAuth) {
		super(requiresAuth);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		Optional tokenParam = Optional.ofNullable(httpServletRequest.getHeader("YABX_ACCESS_TOKEN")); // Authorization:
		String token = httpServletRequest.getHeader("YABX_ACCESS_TOKEN");
		token = StringUtils.removeStart(token, "Bearer").trim();
		Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
		return getAuthenticationManager().authenticate(requestAuthentication);

	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}
}