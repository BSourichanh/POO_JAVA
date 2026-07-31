package fr.campus.poojava.db;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

import java.sql.*;
import java.util.List;

/**
 * Composant de persistance optionnel MySQL pour la sauvegarde et le chargement des personnages et équipements.
 * Sécurisé par des variables d'environnement (DB_URL, DB_USER, DB_PASS).
 *
 * @author BSourichanh
 */
public class Database {
	
	private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/game");
	private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
	private static final String PASSWORD = System.getenv().getOrDefault("DB_PASS", "");
	
	/**
	 * Établit une connexion JDBC avec la base de données MySQL.
	 *
	 * @return L'objet Connection ouvert.
	 * @throws SQLException Si la connexion échoue.
	 */
	public static Connection getConnection () throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	/**
	 * Teste la connectivité vers la base de données MySQL.
	 *
	 * @return true si la connexion réussit, false sinon.
	 */
	public boolean pingSQL () {
		try (Connection connection = Database.getConnection()) {
			System.out.println("Connexion réussie !");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Récupère et affiche la liste de tous les héros enregistrés en base.
	 */
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
	
	/**
	 * Supprime un héros et ses équipements associés de la base dans une transaction ACID atomique.
	 *
	 * @param id L'identifiant du héros.
	 */
	public void removeHero (int id) {
		String sqlEquipOff = "DELETE FROM OffensiveEquipment WHERE characterId = ?";
		String sqlEquipDef = "DELETE FROM DefensiveEquipment WHERE characterId = ?";
		String sqlChar = "DELETE FROM Characters WHERE id = ?";
		
		try (Connection con = Database.getConnection()) {
			con.setAutoCommit(false);
			try (PreparedStatement psOff = con.prepareStatement(sqlEquipOff);
			     PreparedStatement psDef = con.prepareStatement(sqlEquipDef);
			     PreparedStatement psChar = con.prepareStatement(sqlChar)) {
				
				psOff.setInt(1, id);
				psOff.executeUpdate();
				
				psDef.setInt(1, id);
				psDef.executeUpdate();
				
				psChar.setInt(1, id);
				int rows = psChar.executeUpdate();
				
				con.commit();
				if (rows > 0) {
					System.out.println("Héros supprimé avec succès (Transaction ACID validée) !");
				} else {
					System.out.println("Aucun héros trouvé avec cet id.");
				}
			} catch (SQLException e) {
				con.rollback();
				System.err.println("Erreur lors de la suppression du héros : Transaction annulée (Rollback).");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enregistre un personnage et l'ensemble de ses équipements dans la base de données
	 * au sein d'une transaction ACID garantie (Atomique, Cohérente, Isolée, Durable).
	 *
	 * @param player Le personnage à sauvegarder.
	 */
	public void createHero (Character player) {
		String sqlCharacter = """
				INSERT INTO Characters
				(Type, Name, LifePoints, Strength, pos, moveAvailable)
				VALUES (?, ?, ?, ?, ?, ?)
				""";
		
		try (Connection con = Database.getConnection()) {
			con.setAutoCommit(false);
			try (PreparedStatement psCharacter = con.prepareStatement(sqlCharacter, Statement.RETURN_GENERATED_KEYS)) {
				
				psCharacter.setString(1, player.getType().name());
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
				
				con.commit();
				System.out.println("Héros et inventaire sauvegardés avec succès (Transaction ACID validée) !");
			} catch (SQLException e) {
				con.rollback();
				System.err.println("Erreur lors de la sauvegarde du héros : Transaction annulée (Rollback).");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Réinitialise et vide l'ensemble des tables de la base de données dans une transaction ACID atomique.
	 */
	public void clearHeroes () {
		String[] sqls = {
				"SET FOREIGN_KEY_CHECKS = 0",
				"TRUNCATE TABLE OffensiveEquipment",
				"TRUNCATE TABLE DefensiveEquipment",
				"TRUNCATE TABLE Characters",
				"SET FOREIGN_KEY_CHECKS = 1"
		};
		
		try (Connection con = Database.getConnection()) {
			con.setAutoCommit(false);
			try (Statement stmt = con.createStatement()) {
				for (String sql : sqls) {
					stmt.executeUpdate(sql);
				}
				con.commit();
				System.out.println("Base réinitialisée avec succès (Transaction ACID validée) !");
			} catch (SQLException e) {
				con.rollback();
				System.err.println("Erreur lors de la réinitialisation de la base : Transaction annulée (Rollback).");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
