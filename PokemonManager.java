import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PokemonManager {
    private Map<String, Pokemon> pokemonMap;
    private List<Pokemon> userCollection;

    /**
     * @param factory
     */
    public PokemonManager(MapFactory factory) {
        this.pokemonMap = factory.createMap();
        this.userCollection = new ArrayList<>();
    }

    public void loadPokemons(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Saltar la primera línea (encabezado)
    
            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);
    
                if (data.length >= 10) {
                    String name = data[0].trim();
                    int pokedexNumber = Integer.parseInt(data[1].trim());
                    String type1 = data[2].trim();
                    String type2 = data[3].trim();
                    String classification = data[4].trim();
                    double height = Double.parseDouble(data[5].trim());
                    double weight = Double.parseDouble(data[6].trim());
                    String ability = data[7].trim();
                    int generation = Integer.parseInt(data[8].trim());
                    boolean legendary = data[9].trim().equalsIgnoreCase("true");
    
                    pokemonMap.put(name, new Pokemon(name, pokedexNumber, type1, type2, classification, height, weight, ability, generation, legendary));
                } else {
                    System.err.println("Línea inválida: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }
    
    private String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
    
        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        values.add(sb.toString().trim());
        return values.toArray(new String[0]);
    }    

    /**
     * @param name
     * @return
     */
    public Pokemon getPokemon(String name) {
        return pokemonMap.get(name);
    }
    
    /**
     * @return
     */
    public Collection<Pokemon> getAllPokemons() {
        return pokemonMap.values();
    }

    /**
     * @param name
     * @return
     */
    public String addPokemonToCollection(String name) {
        Pokemon pokemon = pokemonMap.get(name);
        if (pokemon == null) {
            return "El Pokémon no se encuentra en la base de datos.";
        }
        if (userCollection.contains(pokemon)) {
            return "El Pokémon ya está en tu colección.";
        }
        userCollection.add(pokemon);
        return "Pokémon agregado a tu colección.";
    }

    /**
     * @return
     */
    public List<Pokemon> getUserCollectionSortedByType() {
        userCollection.sort(Comparator.comparing(Pokemon::getType1));
        return userCollection;
    }

    /**
     * @return
     */
    public List<Pokemon> getAllPokemonsSortedByType() {
        List<Pokemon> sortedList = new ArrayList<>(pokemonMap.values());
        sortedList.sort(Comparator.comparing(Pokemon::getType1));
        return sortedList;
    }

    /**
     * @param ability
     * @return
     */
    public List<Pokemon> getPokemonsByAbility(String ability) {
        List<Pokemon> result = new ArrayList<>();
        for (Pokemon p : pokemonMap.values()) {
            if (p.getAbility().equalsIgnoreCase(ability)) {
                result.add(p);
            }
        }
        return result;
    }
}