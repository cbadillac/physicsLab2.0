JFLAGS = -g
JC = javac
JDOC = javadoc
RM = rm

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

# Compile
CLASSES = \
	Ball.java \
	BallView.java \
	Spring.java \
	SpringView.java \
	SpringAttachable.java \
	FixedHook.java \
	FixedHookView.java \
	LabMenuListener.java \
	MouseListener.java \
	MyWorld.java \
	MyWorldView.java \
	Simulateable.java \
	PhysicsElement.java \
	PhysicsLab.java
	
# Documentation
DOCDIC = documentacion
DOCCLASSES = \
	Ball.java \
	BallView.java \
	MyWorld.java	

default: classes

classes: $(CLASSES:.java=.class)

doc:
	$(JDOC) -d $(DOCDIC) $(DOCCLASSES)

clean:
	$(RM) *.class

cleandoc :
	$(RM) -r $(DOCDIC)
