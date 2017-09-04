#Implementando Contextos Java e Inyección de Dependencia (CDI)

Un inyector, típicamente un contenedor, proporciona los recursos al componente. Aunque la inyección de dependencia puede ser implementado de varias maneras, muchos desarrolladores lo implementan con anotaciones.

JSR 299 puede ser dividido en estos principales paquetes:

    - Alcances y contextos (Scopes and contexts) javax.context
    - Servicios de inyección de dependencias: javax.inject
    - Framework de integración SPI: javax.inject.manager
    - Servicio de notificación de eventos: javax.event
    
JSR 299 se basa fuertemente en anotaciones Java para las especificacón Context and Dependency Injection, JSR 330. JSR 330 contiene un conjunto de anotaciones para usarse en clases inyectables. Las anotaciones son las siguientes:
 
    @Qualifier: Identifica anotaciones de cualificador. Los cualificadores son claves basados en tipos que ayudan a distinguir diferentes usos de objetos del mismo tipo.
    @Inject: Identifica constructores, métodos y campos inyectables.
    @Named: Es un calificador basado en cadena (String)
    @Scope: Identifica anotaciones de alcance
    @Singleton: Identifica un tipo que el inyector instancia solo una vez.
    
 
 ##@Qualifier
 La anotación `@Qualifier` del JSR 330 identifica y especifica la implementación de una clase Java o una interfaz a ser inyectada. 
  
```java  
 @Target({ TYPE, METHOD, PARAMETER, FIELD })
 @Retention(RUNTIME)
 @Documented
 @Qualifier
 public @interface InjectableType {...}
```

##@Inject
La anotación `@Inject` identifica un punto el cual una dependencia en una clase o interfaz Java puede ser inyectada en una clase destino. Esta inyección no solo crea una instancia, o prototipa un objeto por omisión, también puede inyectar un objeto singleton como tal: 

````java
@Stateful
@SessionScoped
@Model
public class ServiceWithInjectedType {
@Inject InjectableType injectable;
...
````

El contenedor buscará el tipo inyectable especificado por @Qualifier y automáticamente inyectará la referencia.
 
 ##@Named
 La anotación @Named proporciona los calificadores basados en cadenas en lugar de los basados en tipo. Un ejemplo de esto es:
 
 http://download.oracle.com/javaee/6/api/javax/inject/Named.html
   
 ````java
 @Named
 public class NamedBusinessType
 implements InjectableType {...}
 ````

##@Scope
Dentro de una aplicación web, un bean tiene que ser capaz de mantener el estado de la duración de la interacción del cliente con la aplicación. La siguiente tabla detalla los alcances de los beans.

|Alcance        |	Anotación           |	Duración   |
|---------------|-----------------------|--------------|
|Requerimiento	|   @RequestScoped	    | La interacción del cliente por un simple requerimiento HTTP |
|Sesión         |	@SessionScoped	    | La interacción del cliente a través de varios requerimientos HTTP |
|Aplicación     |	@ApplicationScoped	| Comparte el estado a través de todas las interacciones de los clientes |
|Dependiente    |	@Dependent	        | Alcance por omisión. Significa un objeto existe para servir exactamente a un cliente (bean), y tiene el mismo ciclo de vida del cliente (bean) |
|Conversación   |	@ConversationScoped	| La interacción del cliente con la aplicación JSF dentro de los dominios del controlador que se extiende a través de múltiples invocaciones del ciclo de vida del JSF |

Las anotaciones de alcance basadas en clase se verían así:

````java
@Stateful
@SessionScoped
@Model
public class ServiceWithInjectedType {
@Inject InjectableType injectableType;
````

Podemos también, crear nuestros propios manejadores de alcance usando la anotación `@Scope`

````java
@java.lang.annotation.Documented
@java.lang.annotation.Retention(RUNTIME)
@javax.inject.Scope
public @interface CustomScoped {}
````

###Importante!!
La inyección se realiza por tipo, no por nombre, de modo tal que si tenemos dos implementaciones de la misma clase y no las cualificamos se producirá una excepción en el arranque. Para evitarlo, podemos hacer uso de @Qualifier para crear nuestras propias anotaciones de cualificación de beans:

````java
@Database
public class DatabaseDrivenSecurityManagerImpl implements SecurityManager { }
	
public class SSOSecurityManagerImpl implements SecurityManager { }
	
@Stateless
public class SomeBean {
    @Inject @Database
	SecurityManager securityManager;
}
	
@Qualifier
@Target({TYPE, METHOD, PARAMETER, FIELD}) 
@Retention(RUNTIME)
public @interface Database {}

````