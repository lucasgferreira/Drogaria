package br.com.drogaria.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto produto;
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;
	private StreamedContent foto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

			novo();
		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Produtos!");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao gerer um novo Produto!");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (produto.getCaminho() == null) {
				Messages.addGlobalWarn("O Campo foto é obrigatório!");
				return;
			}
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto produtoRetorno = produtoDAO.merge(produto);

			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("/home/lucas/eclipse-workspace/Drogaria/arquivos_fotos_drogaria/"
					+ produtoRetorno.getCodigo() + ".png");

			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			produto = new Produto();
			produtos = produtoDAO.listar();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			Messages.addGlobalInfo("Produto salvo com sucesso.");
		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Produto!");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		try {
			produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);

			Path arquivo = Paths.get(
					"/home/lucas/eclipse-workspace/Drogaria/arquivos_fotos_drogaria/" + produto.getCodigo() + ".png");

			Files.deleteIfExists(arquivo);

			Messages.addGlobalInfo("Produto: " + produto.getDescricao() + " foi removido com sucesso.");
			produtos = produtoDAO.listar();
		} catch (RuntimeException | IOException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Produto.");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		try {
			produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");
			produto.setCaminho(
					"/home/lucas/eclipse-workspace/Drogaria/arquivos_fotos_drogaria/" + produto.getCodigo() + ".png");
			;

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.editar(produto);

			produtos = produtoDAO.listar();
			// Messages.addGlobalInfo("Produto editado com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o Produto.");
			e.printStackTrace();
		}
	}

	public void upload(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();

			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(file.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			produto.setCaminho(arquivoTemp.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso!");
		} catch (IOException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar realizar o upload da foto do Produto.");
			e.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable table = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = table.getFilters();
			String pDescricao = (String) filtros.get("descricao");
			String fDescricao = (String) filtros.get("fabricante.descricao");

			String sourceFileName = Faces.getRealPath("/reports/produtos.jasper");
			String caminhoBanner = Faces.getRealPath("/resources/images/banner.png");
			
			Map<String, Object> params = new HashMap<>();
			params.put("CAMINHO_BANNER", caminhoBanner);

			if (pDescricao == null) {
				params.put("PRODUTO_DESCRICAO", "%%");
			} else {
				params.put("PRODUTO_DESCRICAO", "%" + pDescricao + "%");
			}
			if (pDescricao == null) {
				params.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				params.put("FABRICANTE_DESCRICAO", "%" + fDescricao + "%");
			}

			Connection connection = HibernateUtil.getConexao();
			sourceFileName = sourceFileName.replaceAll("jrxml", "jasper");

			JasperPrint print = JasperFillManager.fillReport(sourceFileName, params, connection);
			JasperPrintManager.printReport(print, true);
		} catch (JRException e) {
			// TODO: handle exception
			Messages.addGlobalFatal("Ocorreu um erro ao tentar gerar o relatório!");
			e.printStackTrace();
		}
	}

	public void imagemDownload(ActionEvent event) {
		InputStream stream;
		try {
			produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");

			stream = new FileInputStream(
					"/home/lucas/eclipse-workspace/Drogaria/arquivos_fotos_drogaria/" + produto.getCodigo() + ".png");
			foto = new DefaultStreamedContent(stream, "image/png", produto.getDescricao() + ".png");
			produto = new Produto();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Messages.addGlobalFatal("Ocorreu um erro ao tentar fazer o download da imagem!");
		}

	}
}
