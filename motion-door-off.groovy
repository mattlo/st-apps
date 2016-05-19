definition(
  name: "Light off by motion conditional",
  namespace: "mattlo",
  author: "Matt Lo",
  description: "Turn off lights when theres no motion and a door is open",
  category: "Convenience",
)

preferences {
  section("Turn off when motion is detected"){
    input "motionSensor", "capability.motionSensor", title: "Which Motion Sensor?"
  }
  section("And when the door sensor is closed"){
    input "doorControl", "capability.doorControl", title: "Which Door?"
  }
  section("Turn off lights"){
    input "lights", "capability.switch", title: "Wich Lights?", multiple: true
  }
}


def installed()
{
  subscribe(motionSensor, "motion", motionHandler)
}

def updated()
{
  unsubscribe()
  installed()
}

def uninstalled() {
  unsubscribe()
}

def motionHandler(evt) {
  if (evt.value == "inactive" && doorControl.door === 'open') {
    lights.off()
  }
}