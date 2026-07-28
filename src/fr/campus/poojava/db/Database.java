package fr.campus.poojava.db;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

import java.sql.*;
import java.util.List;

public class Database {

	private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/game");
	private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
	private static final String PASSWORD = System.getenv().getOrDefault("DB_PASS", "");

	public static Connection getConnection () throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public boolean pingSQL () {
		try (Connection connection = Database.getConnection()) {
			System.out.println("Connexion réussie !");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void getHeroes () {
		String sql = "SELECT * FROM Characters";

		try (Connection con = Database.getConnection();
		     Statement stmt = con.createStatement();
		     ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("Id");
				String type = rs.getString("Type");
				String name = rs.getString("Name");
				int life = rs.getInt("LifePoints");

				System.out.println(id + " " + type + " " + name + " " + life);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveOffensiveEquipment (Connection con, int characterId, List<OffensiveEquipment> equipments) throws SQLException {
		String sql = """
				INSERT INTO OffensiveEquipment
				(characterId, name, damage)
				VALUES (?, ?, ?)
				""";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			for (OffensiveEquipment equip : equipments) {
				ps.setInt(1, characterId);
				ps.setString(2, equip.getName());
				ps.setInt(3, equip.getDamage());
				ps.executeUpdate();
			}
		}
	}

	private void saveDefensiveEquipment (Connection con, int characterId, List<DefensiveEquipment> defensiveEquipments) throws SQLException {
		String sql = """
				INSERT INTO DefensiveEquipment
				(characterId, name, hp)
				VALUES (?, ?, ?)
				""";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			for (DefensiveEquipment equip : defensiveEquipments) {
				ps.setInt(1, characterId);
				ps.setString(2, equip.getName());
				ps.setInt(3, equip.getHp());
				ps.executeUpdate();
			}
		}
	}

	public void removeHero (int id) {
		String sql = """
				DELETE FROM Characters
				WHERE id = ?
				""";

		try (Connection con = Database.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Héros supprimé !");
			} else {
				System.out.println("Aucun héros trouvé avec cet id.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createHero (Character player) {
		String sqlCharacter = """
				INSERT INTO Characters
				(Type, Name, LifePoints, Strength, pos, moveAvailable)
				VALUES (?, ?, ?, ?, ?, ?)
				""";

		try (Connection con = Database.getConnection();
		     PreparedStatement psCharacter = con.prepareStatement(sqlCharacter, Statement.RETURN_GENERATED_KEYS)) {

			psCharacter.setString(1, player.getType().toString());
			psCharacter.setString(2, player.getName());
			psCharacter.setInt(3, player.getHp());
			psCharacter.setInt(4, player.getDmg());
			psCharacter.setInt(5, player.getPos());
			psCharacter.setInt(6, player.getMoveAvailable());

			psCharacter.executeUpdate();

			ResultSet rs = psCharacter.getGeneratedKeys();

			if (rs.next()) {
				int characterId = rs.getInt(1);
				saveOffensiveEquipment(con, characterId, player.getOffensiveEquipment());
				saveDefensiveEquipment(con, characterId, player.getDefensiveEquipment());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearHeroes () {
		String[] sqls = {
				"SET FOREIGN_KEY_CHECKS = 0",
				"TRUNCATE TABLE OffensiveEquipment",
				"TRUNCATE TABLE DefensiveEquipment",
				"TRUNCATE TABLE Characters",
				"SET FOREIGN_KEY_CHECKS = 1"
		};

		try (Connection con = Database.getConnection();
		     Statement stmt = con.createStatement()) {

			for (String sql : sqls) {
				stmt.executeUpdate(sql);
			}

			System.out.println("Base réinitialisée.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
