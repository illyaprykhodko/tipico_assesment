package com.tipico.repository;


import com.tipico.model.entity.AbstractEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface JpaBaseRepository<T extends AbstractEntity<? extends ID>, ID> extends JpaRepositoryImplementation<T, ID> {
    JpaEntityInformation<T, ?> getEntityInformation();
}
