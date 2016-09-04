package wordbots

object CodeGenerator {
  def generateJS(node: AstNode): String = g(node)

  private def g(node: AstNode): String = {
    node match {
       // Actions
      case AttributeDelta(target, attr, delta) => s"(function () { actions['attributeDelta'](${g(target)}, ${g(attr)}, ${g(delta)}); })"
      case DealDamage(target, num) => s"(function () { actions['dealDamage'](${g(target)}, $num); })"
      case Destroy(target) => s"(function () { actions['destroy'](${g(target)}); })"
      case Discard(target, num) => s"(function () { actions['discard'](${g(target)}, $num); })"
      case Draw(target, num) => s"(function () { actions['draw'](${g(target)}, $num); })"
      case EnergyDelta(target, delta) => s"(function () { actions['energyDelta'](${g(target)}, ${g(delta)}); })"
      case SetAttribute(target, attr, num) => s"(function () { actions['setAttribute'](${g(target)}, ${g(attr)}, $num); })"

      // Targets
      case Choose(objType, condition) => s"targets['choose'](${g(objType)}, ${g(condition)})"
      case All(objType, condition) => s"targets['all'](${g(objType)}, ${g(condition)})"
      case Self => "targets['self']()"
      case Opponent => "targets['opponent']()"

      // Conditions
      case NoCondition => "null"
      case AttributeComparison(attr, comp) => s"conditions['attributeComparison'](${g(attr)}, ${g(comp)})"

      // Deltas
      case Plus(num) => s"$num"
      case Minus(num) => s"-$num"

      // Comparisons
      case GreaterThanOrEqualTo(num) => s"(function (x) { return x >= $num; })"
      case LessThanOrEqualTo(num) => s"(function (x) { return x <= $num; })"
        
      // Labels
      case l: Label => s"'${getLabelName(l)}'"
    }
  }

  private def getLabelName(label: Label): String = {
    label.getClass.getSimpleName.toLowerCase.split('$')(0)
  }
}