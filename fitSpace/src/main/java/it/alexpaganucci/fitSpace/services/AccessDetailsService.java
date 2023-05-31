package it.alexpaganucci.fitSpace.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.alexpaganucci.fitSpace.auth.AccessDetails;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.repositories.UserRepository;

@Service
public class AccessDetailsService implements UserDetailsService {

	@Autowired
	UserRepository ur;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String x) throws UsernameNotFoundException {
		User u = ur.findUserByEmail(x)
				.orElseThrow(() -> new UsernameNotFoundException("No User with Email '" + x + "' was Found."));
		return AccessDetails.build(u);
	}
}
