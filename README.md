ELO 329 - Tarea 2
=================

Compilación

En éste directorio se encuentra la carpeta de la 'etapa 4'. En esta se
encuentra un 'makefile':

	>> make 		: compila *.java de la etapa 4.
	>> make doc 	: crea documentación de las clases Ball, BallView y 
					  MyWorld en el direcotorio 'documentacion'.
	>> make clean 	: elimina *.class compilados.
	>> make cleandoc: elimina documentación creada.

Ejecución

Una vez ejecutado 'make', se puede correr el programa con

	>>java PhysicsLab

Con esto se despliega una ventana con un menu y un area para visualizar
los elementos físicos.

Para agregar un nuevo elemento se debe ingresar a 'Configuration->Insert'
y se puede seleccionar el elemento deseado. En cada uno se abre un dialogo
con el que se pueden elegir parámetros. El elemento se inserta en una posición
inicial fija.

Para mover los objetos es necesario hacer click sobre ellos y arrastrarlos.
Cuando se quiera adjuntar un elemento a un resorte se debe tomer el elemento
desdeado y soltarlo dentro de un resorte (cerca de la orilla deseada). Un
dialogo preguntara si desea adjuntarlo o no.

No es posible agregar elementos o modificar parámetros de simulación
mientras la simulaciones este corriendo, sólo si esta pausada.
