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
	private final static String RESSOURCES_CHEMIN = "src/main/resources/";
	private final static String TEST_CSV = "test.csv";
	
	
	/**
	 * Cette fonction ouvre le fichier "test.csv" contenant tout les matchs,
	 * puis creer chaque match avec les caractéristiques donnees(NomEquipeHome, NomEquipeAway,
	 * ScoreMatch, ligue, saison) pour chaque ligne remplie.
	 * 
	 * @return liste de matchs
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Match> CsvReaderTest() throws FileNotFoundException, IOException {
		
		List<Match> listMatchsByLigue = new ArrayList<>();
		File File_test = new File(RESSOURCES_CHEMIN + TEST_CSV);
		
		try (CSVReader reader = new CSVReader(new FileReader(File_test), '|')) {
			List<String[]> rows = reader.readAll();
			for (String[] row : rows) {
				for (String e : row) {
					String[] elements = e.split(SEPARATOR);
					
						Match match = new Match();
						match.setNomEquipeHome(elements[0]);
						
						match.setEquipeAway(elements[1]);
						
						match.setScoreHome(Integer.parseInt(elements[2].split("--")[0]));
						match.setScoreAway(Integer.parseInt(elements[2].split("--")[1]));
						
						if(match.getScoreHome() > match.getScoreAway()){
							match.setResultat("Victoire");
						}else if(match.getScoreHome() < match.getScoreAway()){
							match.setResultat("Defaite");
						}else{
							match.setResultat("Nul");
						}

						match.setJournee(Integer.parseInt(elements[3]));
						
						match.setLigue(elements[4]);
						
						match.setSaison(elements[5]);

						listMatchsByLigue.add(match);	
				}
			}
		}
		return listMatchsByLigue;
	}
}