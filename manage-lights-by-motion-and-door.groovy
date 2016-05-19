definition(
  name: "Automatic lights by motion+contact",
  namespace: "mattlo",
  author: "Matt Lo",
  description: "Manage lights based on a motion sensor and contact sensor",
  category: "Convenience",
  iconUrl: "https://www.gravatar.com/avatar/26b312fb15048201616ab0c2115f8ebc?s=256&d=identicon&r=PG",
  iconX2Url: "https://www.gravatar.com/avatar/26b312fb15048201616ab0c2115f8ebc?s=256&d=identicon&r=PG"
)

preferences {
  section("Manage lights with motion detector"){
    input "motionSensor", "capability.motionSensor", title: "Which Motion Sensor?"
  }
  section("And manage with a multi-purpose contact sensor"){
    input "contactSensor", "capability.contactSensor", title: "Which Door?"
  }
  section("Lights"){
    input "lights", "capability.switch", title: "which Lights?", multiple: true
  }
}


def installed()
{
  subscribe(motionSensor, "motion", motionHandler)
  subscribe(contactSensor, "contact", contactHandler)
}

def updated()
{
  unsubscribe()
  installed()
}

def uninstalled() {
  unsubscribe()
}

def contactHandler(evt) {
  if (evt.value == "closed") {
    lights.on()
  } else if(evt.value == "open") {
    lights.off()
  }
}

def motionHandler(evt) {
  if (evt.value == "inactive" && contactSensor.currentValue("contact") == 'open') {
    lights.off()
  } else if (evt.value == "active" && contactSensor.currentValue("contact") == 'open') {
    lights.on()
  }
}