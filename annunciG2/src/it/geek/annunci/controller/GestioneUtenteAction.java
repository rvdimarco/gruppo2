package it.geek.annunci.controller;


import java.util.List;

import it.geek.annunci.factory.ServiceFactory;
import it.geek.annunci.form.UtentiForm;
import it.geek.annunci.model.Prodotto;
import it.geek.annunci.model.Utente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.omg.CORBA.Request;
import org.springframework.beans.BeanUtils;

public class GestioneUtenteAction extends DispatchAction{

	
	private static Logger logger = Logger.getLogger(GestioneUtenteAction.class);
	
	public ActionForward logUtente(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		String forwardPath = "";
		HttpSession session = request.getSession();
		Utente u = (Utente) session.getAttribute("utenteSession");
		
		if(u==null){
			forwardPath="login";
			
			Prodotto p = new Prodotto();
			p.setAcquirente(u);
			
			List<Prodotto> listProdotti = ServiceFactory.getProdottoService().getByWhere(p);
			
			request.setAttribute("listProdotti",listProdotti);
			
			return mapping.findForward(forwardPath);
		} else{
			
			Prodotto p = new Prodotto();
			p.setAcquirente(u);
			
			List<Prodotto> listProdotti = ServiceFactory.getProdottoService().getByWhere(p);
			
			request.setAttribute("listProdotti",listProdotti);
			
			forwardPath="paginaUtente";
			
			return mapping.findForward(forwardPath);
		}
	
	}
	
	public ActionForward login(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		String forwardPath = "";
		UtentiForm uf = (UtentiForm) form;
		Utente u = new Utente();
		
		BeanUtils.copyProperties(uf,u);
		
		List<Utente> utenti = ServiceFactory.getUtenteService().get(u);
		
		if(utenti==null || utenti.isEmpty()){
			forwardPath="failure";
			request.setAttribute("message","Impossibile trovare utente");
			
		
		}else if(utenti.size()>1){
			forwardPath="failure";
			request.setAttribute("message","Database non disponibile momentaneamente..");
			
		}else{
			HttpSession session = request.getSession();
			Utente.incrementaUtentiOnline();
			session.setAttribute("utenteSession",utenti.get(0));
			
			Prodotto p = new Prodotto();
			
			p.setAcquirente(utenti.get(0));
			
			List<Prodotto> listProdotti = ServiceFactory.getProdottoService().getByWhere(p);
			
			request.setAttribute("listProdotti",listProdotti);
			
			forwardPath="paginaUtente";
		}
		
		return mapping.findForward(forwardPath);
		
		
	}
	
	public ActionForward registrazione(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		String forwardPath ="registrazione";
		return mapping.findForward(forwardPath);			
				
				
		}
			
			
	
	public ActionForward eseguiRegistrazione(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{
		
		String forwardPath="";
		
		UtentiForm uf = (UtentiForm) form;
		
		Utente u = new Utente();
		
		BeanUtils.copyProperties(uf,u);
		
		boolean inserito = ServiceFactory.getUtenteService().create(u);
		
		if(inserito){
			HttpSession session = request.getSession();
			session.setAttribute("utenteSession", u);
			forwardPath="paginaUtente";
			return mapping.findForward(forwardPath);
		}else{
			forwardPath="failure";
			return mapping.findForward(forwardPath);
		}
		
	}
	
	public ActionForward visualizzaAnnunci(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		String forwardPath ="visualizzaAnnunci";
		return mapping.findForward(forwardPath);			
				
				
		}
	
	public ActionForward visualizzaProfilo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		String forwardPath ="visualizzaProfilo";
		return mapping.findForward(forwardPath);			
				
				
	}
	public ActionForward logout(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		HttpSession session = request.getSession();
		session.removeAttribute("utenteSession");
		Utente.decrementaUtentiOnline();
	
		session.invalidate();
	
		String forwardPath="home";
		return mapping.findForward(forwardPath);
		
	}
	
}