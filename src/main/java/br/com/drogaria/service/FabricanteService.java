package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.domain.Fabricante;

// http://localhost:8080/Drogaria/rest/fabricante
@Path("fabricante")
public class FabricanteService {
	@GET
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listar("descricao");

		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);

		return json;
	}

	// http://127.0.0.1:8080/Drogaria/rest/fabricante/{codigo}
	// http://127.0.0.1:8080/Drogaria/rest/fabricante/10
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") Long codigo) {
		
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(codigo);

		Gson gson = new Gson();
		String json = gson.toJson(fabricante);

		return json;
	}
}
