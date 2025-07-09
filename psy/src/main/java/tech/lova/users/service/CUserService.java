package tech.lova.users.service;

import java.time.Clock;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.lova.users.domain.CUser;
import tech.lova.users.domain.CUserRepository;

@Service
@PreAuthorize("isAuthenticated()")
public class CUserService {

	private final CUserRepository repository;

	CUserService(CUserRepository repository, Clock clock) {
		this.repository = repository;
	}

	public int count() {
		return (int) repository.count();
	}

	@Transactional
	public void create(String name, String email, String password) {
		if ("fail".equals(name))
			throw new RuntimeException("This is for testing the error handler");
		var item = new CUser();
		item.setEmail(email);
		item.setName(name);
		item.setPassword(password);
		repository.saveAndFlush(item);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Optional<CUser> get(Long id) {
		return repository.findById(id);
	}

	public Page<CUser> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<CUser> list(Pageable pageable, Specification<CUser> filter) {
		return repository.findAll(filter, pageable);
	}

	public CUser save(CUser entity) {
		return repository.save(entity);
	}

}