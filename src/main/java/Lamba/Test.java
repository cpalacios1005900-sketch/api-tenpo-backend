package Lamba;

public class Test {
	
	 // numero pares 
	
	List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);

	List<Integer> pares = numeros.stream()
	                             .filter(n -> n % 2 == 0)
	                             .collect(Collectors.toList());

	System.out.println(pares); // [2, 4, 6]
	
	
	
	List<String> nombres = Arrays.asList("Ana", "Luis", "Carlos");

	List<String> mayusculas = nombres.stream()
	                                 .map(String::toUpperCase)
	                                 .collect(Collectors.toList());
	
	 // numero mayuscar 

	System.out.println(mayusculas); // [ANA, LUIS, CARLOS]
	
	
	 // suma
	
	List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

	int suma = numeros.stream()
	                  .reduce(0, (a, b) -> a + b);

	System.out.println(suma); //
	
	 // filtrar
	
	
	class Cliente {
	    String nombre;
	    int edad;
	    Cliente(String n, int e) { nombre = n; edad = e; }
	}

	List<Cliente> clientes = Arrays.asList(
	    new Cliente("Ana", 30),
	    new Cliente("Luis", 22),
	    new Cliente("Carlos", 27)
	);

	List<Cliente> filtrados = clientes.stream()
	                                  .filter(c -> c.edad > 25)
	                                  .sorted(Comparator.comparing(c -> c.nombre))
	                                  .collect(Collectors.toList());

	filtrados.forEach(c -> System.out.println(c.nombre));
	
	
	//  agruoar 
	class Cliente {
	    String nombre;
	    String ciudad;
	    Cliente(String n, String c) { nombre = n; ciudad = c; }
	}

	List<Cliente> clientes = Arrays.asList(
	    new Cliente("Ana", "Bogotá"),
	    new Cliente("Luis", "Cali"),
	    new Cliente("Carlos", "Bogotá")
	);

	Map<String, List<Cliente>> porCiudad = clientes.stream()
	                                               .collect(Collectors.groupingBy(c -> c.ciudad));

	porCiudad.forEach((ciudad, lista) -> System.out.println(ciudad + ": " + lista.size()));
	// Bogotá: 2
	// Cali: 1
	
	
	
	Optional<Cliente> mayor = clientes.stream()
            .max(Comparator.comparing(c -> c.edad));

mayor.ifPresent(c -> System.out.println(c.nombre + " - " + c.edad));


// Ejercicio 7: Mapear y reducir

int sumaEdad = clientes.stream()
.filter(c -> c.edad > 25)
.map(c -> c.edad)
.reduce(0, Integer::sum);

System.out.println(sumaEdad);


long count = clientes.stream()
.filter(c -> c.ciudad.equals("Bogotá"))
.count();

System.out.println(count); // 2

//Promedio de edades:




double promedio = clientes.stream()
.mapToInt(c -> c.edad)
.average()
.orElse(0);


// Filtrar y limitar resultados:

List<Cliente> top2 = clientes.stream()
                             .sorted(Comparator.comparing(c -> c.edad))
                             .limit(2)
                             .collect(Collectors.toList());

//Filtrar y limitar resultados:
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 3, 3);
List<Integer> sinDuplicados = nums.stream().distinct().collect(Collectors.toList());




}
