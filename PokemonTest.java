import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class PokemonManagerTest {
    
    private PokemonManager manager;

    /**
     * 
     */
    @BeforeEach
    void setUp() {
        manager = new PokemonManager(new HashMapFactory());
        manager.loadPokemons("pokemon_data.csv");
    }

    /**
     * 
     */
    @Test
    void testAddPokemonToCollection() {
        String result = manager.addPokemonToCollection("Lapras");
        assertEquals("Lapras ha sido agregado a la colección.", result, "El mensaje debería indicar que Lapras fue agregado correctamente");

        String duplicateResult = manager.addPokemonToCollection("Lapras");
        assertEquals("Lapras ya está en la colección.", duplicateResult, "No debería permitir agregar el mismo Pokémon dos veces");
    }

    /**
     * 
     */
    @Test
    void testGetPokemon() {
        Pokemon lapras = manager.getPokemon("Lapras");
        assertNotNull(lapras, "Lapras debería existir en la base de datos");
        assertEquals("Lapras", lapras.getName(), "El nombre del Pokémon debería ser Lapras");
    }

    /**
     * 
     */
    @Test
    void testGetPokemonsByAbility() {
        List<Pokemon> waterAbsorbPokemons = manager.getPokemonsByAbility("Water-absorb, Shell-armor, Hydration");
        assertTrue(waterAbsorbPokemons.stream().anyMatch(p -> p.getName().equals("Lapras")), "Lapras debería estar en la lista de Pokémon con sus habilidades");
    }
}