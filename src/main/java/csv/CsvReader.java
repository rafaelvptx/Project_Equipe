package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Match;
import com.opencsv.CSVReader;

public class CsvReader {

	private final static String SEPARATOR = ";";
	private final static File RESSOURCES = new File("src/main/resources/csv/");


	/**
	 * Cette fonction ouvre tous les fichiers du repertoire "src/main/resources/csv/"  contenant l'ensemble des matchs,
	 * puis creer chaque match avec les caractéristiques donnees(NomEquipeHome, NomEquipeAway,
	 * ScoreMatch, ligue, saison) pour chaque ligne remplie.
	 * Chaque fichiers represente une ligue et une saison disctincte
	 * 
	 * @return liste de matchs
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Match> CsvReaderTest() throws FileNotFoundException, IOException {

		List<Match> listMatchsByLigue = new ArrayList<>();

		File[] listFiles = RESSOURCES.listFiles();

		for (File f : listFiles){

			try (CSVReader reader = new CSVReader(new FileReader(f), '|')) {
				List<String[]> rows = reader.readAll();
				for (String[] row : rows) {
					for (String e : row) {
						String[] elements = e.split(SEPARATOR);

						Match match = new Match();
						match.setNomEquipeHome(elements[0]);

						match.setEquipeAway(elements[1]);

						if(!elements[2].isEmpty()){
							match.setScoreHome(Integer.parseInt(elements[2].split("--")[0]));
							match.setScoreAway(Integer.parseInt(elements[2].split("--")[1]));	

							if(match.getScoreHome() > match.getScoreAway()){
								match.setResultat("Victoire");
							}else if(match.getScoreHome() < match.getScoreAway()){
								match.setResultat("Defaite");
							}else{
								match.setResultat("Nul");
							}
						}else{
							match.setScoreHome(-1);
							match.setScoreAway(-1);
							match.setResultat("Non joue");
						}

						match.setJournee(Integer.parseInt(elements[3]));

						match.setLigue(elements[4]);

						match.setSaison(elements[5]);

						listMatchsByLigue.add(match);	
					}
				}
			}
		}
		return listMatchsByLigue;
	}
}