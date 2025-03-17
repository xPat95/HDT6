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

        long startTime = System.nanoTime();
        manager.loadPokemons("./HDT6/pokemon_data.csv");
        long endTime = System.nanoTime();
        System.out.println("Tiempo de carga de Pokémon: " + ((endTime - startTime) / 1_000_000.0) + " ms");

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
                    startTime = System.nanoTime();
                    String resultado = manager.addPokemonToCollection(nombreAgregar);
                    endTime = System.nanoTime();
                    System.out.println(resultado);
                    System.out.println("Tiempo de ejecución: " + ((endTime - startTime) / 1_000_000.0) + " ms");
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del Pokémon: ");
                    String nombre = scanner.nextLine();
                    startTime = System.nanoTime();
                    Pokemon p = manager.getPokemon(nombre);
                    endTime = System.nanoTime();
                    if (p != null) {
                        System.out.println("\nNombre: " + p.getName());
                        System.out.println("Número Pokédex: " + p.getPokedexNumber());
                        System.out.println("Tipo 1: " + p.getType1());
                        System.out.println("Tipo 2: " + (p.getType2().isEmpty() ? "Ninguno" : p.getType2()));
                        System.out.println("Clasificación: " + p.getClassification());
                        System.out.println("Altura: " + p.getHeight() + " m");
                        System.out.println("Peso: " + p.getWeight() + " kg");
                        System.out.println("Habilidad: " + p.getAbility());
                        System.out.println("Generación: " + p.getGeneration());
                        System.out.println("Legendario: " + (p.isLegendary() ? "Sí" : "No"));
                    } else {
                        System.out.println("El Pokémon no existe en la base de datos.");
                    }
                    System.out.println("Tiempo de ejecución: " + ((endTime - startTime) / 1_000_000.0) + " ms");
                    break;

                case 3:
                    System.out.println("\nColección del usuario ordenada por Tipo 1:");
                    startTime = System.nanoTime();
                    List<Pokemon> userCollection = manager.getUserCollectionSortedByType();
                    endTime = System.nanoTime();
                    for (Pokemon poke : userCollection) {
                        System.out.println("Nombre: " + poke.getName());
                        System.out.println("Número Pokédex: " + poke.getPokedexNumber());
                        System.out.println("Tipo 1: " + poke.getType1());
                        System.out.println("Tipo 2: " + (poke.getType2().isEmpty() ? "Ninguno" : poke.getType2()));
                        System.out.println("Clasificación: " + poke.getClassification());
                        System.out.println("Altura: " + poke.getHeight() + " m");
                        System.out.println("Peso: " + poke.getWeight() + " kg");
                        System.out.println("Habilidad: " + poke.getAbility());
                        System.out.println("Generación: " + poke.getGeneration());
                        System.out.println("Legendario: " + (poke.isLegendary() ? "Sí" : "No"));
                        System.out.println("--------------------------------------");
                    }
                    System.out.println("Tiempo de ejecución: " + ((endTime - startTime) / 1_000_000.0) + " ms");
                    break;

                case 4:
                    System.out.println("\nTodos los Pokémon ordenados por Tipo 1:");
                    startTime = System.nanoTime();
                    List<Pokemon> sortedPokemons = manager.getAllPokemonsSortedByType();
                    endTime = System.nanoTime();
                    String currentType = "";
                    for (Pokemon poke : sortedPokemons) {
                        if (!poke.getType1().equals(currentType)) {
                            currentType = poke.getType1();
                            System.out.println("\n" + currentType + ":");
                        }
                        System.out.println("Nombre: " + poke.getName());
                        System.out.println("Número Pokédex: " + poke.getPokedexNumber());
                        System.out.println("Tipo 1: " + poke.getType1());
                        System.out.println("Tipo 2: " + (poke.getType2().isEmpty() ? "Ninguno" : poke.getType2()));
                        System.out.println("Clasificación: " + poke.getClassification());
                        System.out.println("Altura: " + poke.getHeight() + " m");
                        System.out.println("Peso: " + poke.getWeight() + " kg");
                        System.out.println("Habilidad: " + poke.getAbility());
                        System.out.println("Generación: " + poke.getGeneration());
                        System.out.println("Legendario: " + (poke.isLegendary() ? "Sí" : "No"));
                        System.out.println("--------------------------------------");
                    }
                    System.out.println("Tiempo de ejecución: " + ((endTime - startTime) / 1_000_000.0) + " ms");
                    break;

                case 5:
                    System.out.print("Ingrese la habilidad del Pokémon a buscar: ");
                    String habilidad = scanner.nextLine();
                    startTime = System.nanoTime();
                    List<Pokemon> filteredPokemons = manager.getPokemonsByAbility(habilidad);
                    endTime = System.nanoTime();
                    if (filteredPokemons.isEmpty()) {
                        System.out.println("No se encontraron Pokémon con la habilidad: " + habilidad);
                    } else {
                        System.out.println("\nPokémon con la habilidad " + habilidad + ":");
                        for (Pokemon poke : filteredPokemons) {
                            System.out.println("Nombre: " + poke.getName());
                            System.out.println("Número Pokédex: " + poke.getPokedexNumber());
                            System.out.println("Tipo 1: " + poke.getType1());
                            System.out.println("Tipo 2: " + (poke.getType2().isEmpty() ? "Ninguno" : poke.getType2()));
                            System.out.println("Clasificación: " + poke.getClassification());
                            System.out.println("Altura: " + poke.getHeight() + " m");
                            System.out.println("Peso: " + poke.getWeight() + " kg");
                            System.out.println("Habilidad: " + poke.getAbility());
                            System.out.println("Generación: " + poke.getGeneration());
                            System.out.println("Legendario: " + (poke.isLegendary() ? "Sí" : "No"));
                            System.out.println("--------------------------------------");
                        }
                    }
                    System.out.println("Tiempo de ejecución: " + ((endTime - startTime) / 1_000_000.0) + " ms");
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