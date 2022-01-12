package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Vene;

public class Dao {
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql;
	private String db ="Venekanta.sqlite";

	private Connection yhdistaTietokantaan() {
		Connection con = null;
		String path = System.getProperty("catalina.base");
		path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/");
		String url = "jdbc:sqlite:" + path + db;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public ArrayList<Vene> listaaVeneet() {
		ArrayList<Vene> veneet = new ArrayList<>();
		sql = "SELECT * FROM veneet";

		try {
			con = yhdistaTietokantaan();
			if(con != null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);
				rs = stmtPrep.executeQuery();
				if(rs != null) {
					while(rs.next()){
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getDouble(4));
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));
						veneet.add(vene);
					}
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return veneet;
	}

	public ArrayList<Vene>listaaVeneet(String hakusana){
		ArrayList<Vene> veneet = new ArrayList<>();
		sql = "SELECT * FROM veneet WHERE nimi LIKE ? OR merkkimalli LIKE ? OR pituus LIKE ? OR leveys LIKE ? OR hinta LIKE ?";
		try {
			con = yhdistaTietokantaan();
			if (con != null) {
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				stmtPrep.setString(3, "%" + hakusana + "%");
				stmtPrep.setString(4, "%" + hakusana + "%");
				stmtPrep.setString(4, "%" + hakusana + "%");
				rs = stmtPrep.executeQuery();
				if (rs!=null) {
					while(rs.next()){
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getDouble(4));
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));
						veneet.add(vene);
					}
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return veneet;
	}

	public boolean poistaVene(String tunnus){
		boolean poistonOnnistui = true;
		sql = "DELETE FROM veneet WHERE tunnus=?";
		try {
			con = yhdistaTietokantaan();
			stmtPrep = con.prepareStatement(sql);
			stmtPrep.setString(1, tunnus);
			stmtPrep.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			poistonOnnistui=false;
		}
		return poistonOnnistui;
	}

	public boolean lisaaVene(Vene vene){
		boolean lisaysOnnistui=true;
		sql="INSERT INTO veneet (nimi,merkkimalli,pituus,leveys,hinta) VALUES (?,?,?,?,?)";
		try {
			con = yhdistaTietokantaan();
			stmtPrep = con.prepareStatement(sql);
			stmtPrep.setString(1, vene.getNimi());
			stmtPrep.setString(2, vene.getMerkkimalli());
			stmtPrep.setDouble(3, vene.getPituus());
			stmtPrep.setDouble(4, vene.getLeveys());
			stmtPrep.setInt(5, vene.getHinta());
			stmtPrep.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			lisaysOnnistui=false;
		}
		return lisaysOnnistui;
	}

	public Vene etsiVene(String tunnus) {
		Vene vene = null;
		sql = "SELECT * FROM veneet WHERE tunnus=?";
		try {
			con = yhdistaTietokantaan();
			if(con != null){
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, tunnus);
				rs = stmtPrep.executeQuery();
				if(rs.isBeforeFirst()){
					rs.next();
					vene = new Vene();
					vene.setTunnus(rs.getInt(1));
					vene.setNimi(rs.getString(2));
					vene.setMerkkimalli(rs.getString(3));
					vene.setPituus(rs.getDouble(4));
					vene.setLeveys(rs.getDouble(5));
					vene.setHinta(rs.getInt(6));
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vene;
	}

	public boolean muutaVene(Vene vene){
		boolean muutosOnnistui=true;
		sql="UPDATE veneet SET nimi=?, merkkimalli=?, pituus=?, leveys=?, hinta=? WHERE tunnus=?";
		try {
			con = yhdistaTietokantaan();
			stmtPrep = con.prepareStatement(sql);
			stmtPrep.setString(1, vene.getNimi());
			stmtPrep.setString(2, vene.getMerkkimalli());
			stmtPrep.setDouble(3, vene.getPituus());
			stmtPrep.setDouble(4, vene.getLeveys());
			stmtPrep.setInt(5, vene.getHinta());
			stmtPrep.setInt(6, vene.getTunnus());
			stmtPrep.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			muutosOnnistui=false;
		}
		return muutosOnnistui;
	}
}