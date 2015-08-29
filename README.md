# Physics-Engine-Demos
Demonstrations using the physics engine library

##Running the Program
Java3D may not work on OSX and you may need additional files to run this program on Linux. Try running this on a Windows machine, preferably. To run the demos, you should download the project and run Demo.jar. You will need j3dcore-ogl.dll in the same directory as Demo.jar, though. If you're using a different operating system, you may need a different j3dcore-ogl file.

##Details
This program uses my physics engine library found at https://github.com/mjchao/Physics-Engine-Library . It was created towards the end of July 2013 as the end of my timebox for developing the physics engine approached. The graphics are rendered using an old version of Java 3D. Java 3D has been replaced by Java FX in Java 8.

It features

* Kinematics
* Circular Motion
* Angular Momentum
* Magnetism
* Simple Harmonic Motion

In Kinematics, we have a ball moving with some initial velocity that the user may set. It is pulled back down to the ground by gravity and bounces a bit.

In Circular Motion, we have a small mass orbiting an immobile larger mass. You can speed up the smaller mass by pressing 1 and slow it down by pressing 2. Make sure you click the "Use Keyboard" button first though, as keypresses are detected through that button; detecting keypresses on the Java 3D scene proved overly complicated. During development, I also did not have a good conversion from meters to pixels, and so the inputs to the simulation were empirically determined. 

In Angular Momentum, we have a box falling onto a sphere. The box and sphere then bounce around forever as there is no friction. You may apply additional forces with the keyboard if desired. Use "control" to toggle between the ball and the sphere. Use "shift" to toggle applying a positive or negative force. Use "1", "2", "3" to apply a force in the x, y, z directions and use "4", "5", "6" to apply a torque about the x, y, and z axes. The 3D collision handling is a bit buggy, though.

In Magnetism, we attempt to simulate a motor, except I could not create a nice square loop graphic. Therefore, the user must apply his/her imagination. On the screen, we have a charged box, instead of a loop. If we give it a velocity and apply a magnetic field to it, the box will experience a force (F = qv x B). We can also fix the box and instead pretend it is a loop of wire with a current. Then, applying a magnetic field to it will make the loop rotate, as a motor does. You can modify the mass, charge, position, velocity/current, direction of the B field and magnitude of the B field.

In Simple Harmonic Motion, we explore the unsual property that the center of mass of a system moves as if all the mass were centered there. In this simulation, we have two masses connected to a spring. We give the two masses different initial velocities, and the movement of these two masses under the influence of the spring is anything but simple. However, we indicate the center of mass with a white sphere. Notice that this sphere moves with a constant velocity.
