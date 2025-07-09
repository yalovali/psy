package tech.lova.users.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * CUserRepository is the repository interface for managing CUser entities. It
 * extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor
 * for advanced querying capabilities.
 */
public interface CUserRepository extends JpaRepository<CUser, Long>, JpaSpecificationExecutor<CUser> {
//public interface CUserRepository extends JpaRepository<CUser, Long>, JpaSpecificationExecutor<CUser> {

	// If you don't need a total row count, Slice is better than Page as it only
	// performs a select query.
	// Page performs both a select and a count query.
	Slice<CUser> findAllBy(Pageable pageable);
}
