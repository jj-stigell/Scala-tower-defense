package TowerGame

class Tower(val place: Vector2D) {


  private var onCooldown = false

  private var coolDownCounter = 0



  def shoot() = {

    if (onCooldown) {

      coolDownCounter -= Settings.coolDownPerCycle

      if (coolDownCounter == 0) {
        onCooldown = false
      }




    } else {

      // shoot near by enemy, search enemies on proximity

      onCooldown = true
      coolDownCounter = Settings.coolDownTime


    }



  }





}
