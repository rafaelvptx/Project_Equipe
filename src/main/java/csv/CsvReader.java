package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Match;
import com.opencsv.CSVReader;

public class CsvReader {

	private final static String SEPARATOR = ";";
	private final static String RESOURCES_CHEMIN = "src/main/resources/";
	private final static String TEST_CSV = "test.csv";
	
	public Map<String, List<Match>> CsvReaderTest() throws FileNotFoundException, IOException {
		Map<String, List<Match>> cores = new HashMap<>();
		File File_test = new File(RESOURCES_CHEMIN + TEST_CSV);
		try (CSVReader reader = new CSVReader(new FileReader(File_test), '|')) {
			List<String[]> rows = reader.readAll();
			for (String[] row : rows) {
				for (String e : row) {
					String[] separe = e.split(SEPARATOR);
					
						Match match = new Match();
						match.setNomEquipeHome(separe[0]);
						match.setEquipeAway(separe[1]);
						match.setScoreHome(Integer.parseInt(separe[2].split("--")[0]));
						match.setScoreAway(Integer.parseInt(separe[2].split("--")[1]));
						if(match.getScoreHome() > match.getScoreAway()){
							match.setResultat("Victoire");
						}else if(match.getScoreHome() < match.getScoreAway()){
							match.setResultat("Defaite");
						}else{
							match.setResultat("Nul");
						}

						
						match.setJournee(Integer.parseInt(separe[3]));
						match.setLigue(separe[4]);
						match.setSaison(separe[5]);
						if(cores.containsKey(separe[0])){
							List<Match> l = cores.get(separe[0]);
							l.add(match);
							cores.put(separe[0], l);
						}else{
							List<Match> l = new ArrayList<>();
							l.add(match);
							cores.put(separe[0], l);
						}
						
						
						
						
				}
			}
		}
		return cores;
	}
}