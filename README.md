# Physics-Engine-Demos
Demonstrations using the physics engine library

This program uses my physics engine library found at https://github.com/mjchao/Physics-Engine-Library .

It features

* Kinematics
* Circular Motion
* Angular Momentum
* Magnetism
* Simple Harmonic Motion

In Kinematics, we have a ball moving with some initial velocity that the user may set. It is pulled back down to the ground by gravity.

In Circular Motion, we have a small mass orbiting an immobile larger mass. You can speed up the larger mass by pressing 1 and slow it down by pressing 2.

In Angular Momentum, we have a box falling onto a sphere. The box and sphere then bounce around forever as there is no friction. You may apply additional forces with the keyboard if desired. Use "control" to toggle between the ball and the sphere. Use "shift" to toggle applying a positive or negative force. Use "1", "2", "3" to apply a force in the x, y, z directions and use "4", "5", "6" to apply a torque about the x, y, and z axes.

In Magnetism, we attempt to simulate a motor, except we are unable to create nice graphics. Therefore, the user must apply his/her imagination. On the screen, we have a charged box. If we give it a velocity and apply a magnetic field to it, the box will experience a force (F = qv x B). We can also fix the box and instead pretend it is a loop of wire with a current. Then, applying a magnetic field to it will make the loop rotate, as a motor does. You can modify the mass, charge, position, velocity/current, direction of the B field and magnitude of the B field.

In Simple Harmonic Motion, we explore the unsual property that the center of mass of a system moves as if all the mass were centered there. In this simulation, we have two masses connected to a spring. We give the two masses different initial velocities, and the movement of these two masses under the influence of the spring is anything but simple. However, we indicate the center of mass with a white sphere. Notice that this sphere moves with a constant velocity.
