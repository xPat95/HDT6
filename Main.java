import java.util.Scanner;
import java.util.Map;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MapFactory factory = null;

        while (factory == null) {
            System.out.println("Seleccione la implementación de Map:");
            System.out.println("1. HashMap");
            System.out.println("2. TreeMap");
            System.out.println("3. LinkedHashMap");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    factory = new HashMapFactory();
                    break;
                case 2:
                    factory = new TreeMapFactory();
                    break;
                case 3:
                    factory = new LinkedHashMapFactory();
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.\n");
            }
        }

        PokemonManager manager = new PokemonManager(factory);
        manager.loadPokemons("pokemon_data.csv");

        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Agregar un Pokémon a la colección del usuario");
            System.out.println("2. Mostrar datos de un Pokémon");
            System.out.println("3. Mostrar la colección del usuario ordenada por Tipo 1");
            System.out.println("4. Mostrar todos los Pokémon ordenados por Tipo 1");
            System.out.println("5. Buscar Pokémon por habilidad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcionMenu = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcionMenu) {
                case 1:
                    System.out.print("Ingrese el nombre del Pokémon a agregar: ");
                    String nombreAgregar = scanner.nextLine();
                    String resultado = manager.addPokemonToCollection(nombreAgregar);
                    System.out.println(resultado);
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del Pokémon: ");
                    String nombre = scanner.nextLine();
                    Pokemon p = manager.getPokemon(nombre);
                    if (p != null) {
                        System.out.println("Nombre: " + p.getName());
                        System.out.println("Tipo 1: " + p.getType1());
                        System.out.println("Tipo 2: " + p.getType2());
                        System.out.println("Habilidad: " + p.getAbility());
                    } else {
                        System.out.println("El Pokémon no existe en la base de datos.");
                    }
                    break;

                case 3:
                    System.out.println("\nColección del usuario ordenada por Tipo 1:");
                    List<Pokemon> userCollection = manager.getUserCollectionSortedByType();
                    for (Pokemon poke : userCollection) {
                        System.out.println("Nombre: " + poke.getName() + " | Tipo1: " + poke.getType1() + " | Tipo2: " + poke.getType2());
                    }
                    break;

                case 4:
                    System.out.println("\nTodos los Pokémon ordenados por Tipo 1:");
                    List<Pokemon> sortedPokemons = manager.getAllPokemonsSortedByType();
                    String currentType = "";
                    for (Pokemon poke : sortedPokemons) {
                        if (!poke.getType1().equals(currentType)) {
                            currentType = poke.getType1();
                            System.out.println("\n" + currentType + ":");
                        }
                        System.out.println("  - " + poke.getName() + " | Tipo2: " + poke.getType2());
                    }
                    break;

                case 5:
                    System.out.print("Ingrese la habilidad del Pokémon a buscar: ");
                    String habilidad = scanner.nextLine();
                    List<Pokemon> filteredPokemons = manager.getPokemonsByAbility(habilidad);
                    if (filteredPokemons.isEmpty()) {
                        System.out.println("No se encontraron Pokémon con la habilidad: " + habilidad);
                    } else {
                        System.out.println("\nPokémon con la habilidad " + habilidad + ":");
                        for (Pokemon poke : filteredPokemons) {
                            System.out.println("Nombre: " + poke.getName() + " | Tipo1: " + poke.getType1() + " | Tipo2: " + poke.getType2());
                        }
                    }
                    break;

                case 6:
                    ejecutando = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}