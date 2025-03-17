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
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 10) { 
                    String name = data[1];
                    String type1 = data[2];
                    String type2 = data[3];
                    String ability = data[4];
                    pokemonMap.put(name, new Pokemon(name, type1, type2, ability));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
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