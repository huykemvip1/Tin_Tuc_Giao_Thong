package com.tintucgiaothong.data.Repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

//---- test
public class SequenceCustom implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Query query= session.createQuery("select m.id from MaTheoDoi m order by m.id desc", Long.class);
		List<Long> kq=query.getResultList();
		if (kq.size() < 1) {
			return Long.valueOf(1);
		}else {
			int check=0;
			for (Long vitri : kq) {
				check++;
				if (check != vitri) {
					return Long.valueOf(check);
				}
			}
			return Long.valueOf(check+1);
		}
		
	}

}
