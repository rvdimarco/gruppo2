package it.geek.annunci.service;

import java.util.List;

import it.geek.annunci.dao.UtenteDao;
import it.geek.annunci.model.Annuncio;
import it.geek.annunci.model.Utente;

public class UtenteService implements UtenteServiceInterface{
	
	private UtenteDao utenteDao;
	
	public void setUtenteDao(UtenteDao utenteDao){
		this.utenteDao=utenteDao;
	}
	
	public List<Utente> get(Utente u){
		
		List<Utente> utRet = null;
		
		if(u != null){
			
			utRet = utenteDao.findByWhere(u);
		}
		
		return utRet;
	}


	public Utente getId(int id){
		
		Utente u = null;
		
		if(id!=0){
			
			u = utenteDao.findById(id);
			
		}
		return u;
		
	}

	public boolean create(Utente u) {
		boolean ret = utenteDao.insert(u);
		return ret;
	}

	public Utente getAndUpdate(Utente u){
		
		
	
		boolean ret = utenteDao.update(u);
		
		
		return u;
		
		
	}
	
	public List<Utente> getAll(){
		
		List<Utente> ret = null;
		try {
			ret = utenteDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		
	}
		return ret;
		
	}
	
	public void elimina (int id){
		
		Utente u = null;		
		utenteDao.delete(u.getCodiceUtente());
			
	}
	
}
