package com.interfija.masterposmultitenant.repository.branch;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchConfigurationRepository extends JpaRepository<BranchConfigurationEntity, Long> {

}
