package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.Vene;
import model.dao.Dao;

@WebServlet("/veneet/*")
public class Veneet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Veneet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		Dao dao = new Dao();
		ArrayList<Vene> veneet;
		String strJSON = "";
		if (pathInfo == null) {
			veneet = dao.listaaVeneet();
			strJSON = new JSONObject().put("veneet", veneet).toString();
		} else if (pathInfo.indexOf("haevene") != -1) {
			String tunnus = pathInfo.replace("/haevene/", "");
			if (tunnus == "" || tunnus.equals("/haevene")) {
				strJSON = "{}";
			} else {
				Vene vene = dao.etsiVene(tunnus);
				JSONObject JSON = new JSONObject();
				JSON.put("tunnus", vene.getTunnus());
				JSON.put("nimi", vene.getNimi());
				JSON.put("merkkimalli", vene.getMerkkimalli());
				JSON.put("pituus", vene.getPituus());
				JSON.put("leveys", vene.getLeveys());
				JSON.put("hinta", vene.getHinta());
				strJSON = JSON.toString();
			}
		} else {
			String hakusana = pathInfo.replace("/", "");
			veneet = dao.listaaVeneet(hakusana);
			strJSON = new JSONObject().put("veneet", veneet).toString();
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Vene vene = new Vene();
		vene.setNimi(jsonObj.getString("nimi"));
		vene.setMerkkimalli(jsonObj.getString("merkkimalli"));
		vene.setPituus(jsonObj.getDouble("pituus"));
		vene.setLeveys(jsonObj.getDouble("leveys"));
		vene.setHinta(jsonObj.getInt("hinta"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.lisaaVene(vene)){
			out.println("{\"response\":1}");
		} else {
			out.println("{\"response\":0}");
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Vene vene = new Vene();
		vene.setTunnus(jsonObj.getInt("tunnus"));
		vene.setNimi(jsonObj.getString("nimi"));
		vene.setMerkkimalli(jsonObj.getString("merkkimalli"));
		vene.setPituus(jsonObj.getDouble("pituus"));
		vene.setLeveys(jsonObj.getDouble("leveys"));
		vene.setHinta(jsonObj.getInt("hinta"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.muutaVene(vene)){
			out.println("{\"response\":1}");
		} else {
			out.println("{\"response\":0}");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String tunnus = pathInfo.replace("/", "");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.poistaVene(tunnus)){
			out.println("{\"response\":1}");
		} else {
			out.println("{\"response\":0}");
		}
	}
}
