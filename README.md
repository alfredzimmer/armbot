# Arm Bot

## Structure
```markdown
- src: The source files
    - main: The source code
        - net.ironpulse.armbot: The root of the source code
            - commands: All commands
            - drivers: The driver adapt the hardware to Java (gyros, swerve module, etc.)
            - maths: All maths utils
            - models: The data classes
            - state: State Machine Framework
            - subsystems: All subsystems (SwerveSubsystem, ArmSubsystem, etc.)
            - RobotContainer.java: Registration of key binding and subsystems
            - Robot.java: Manage the robot
            - Main.java: The entry of the all code
            - Constants.java: Save all the constants (PID parameters, length of the robot, etc.)
    - test: The unit tests
- vendordeps: The dependencies using vendordeps
```