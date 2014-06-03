package org.twooldguys.homestay

sealed trait Smoking
case object EnjoysSmoking extends Smoking
case object NonSmoking    extends Smoking

sealed trait Pets
case object YesPets extends Pets
case object NoPets  extends Pets

sealed trait Children
case object NoChild        extends Children
case object OneChild       extends Children
case object TwoChild       extends Children
case object ThreePlusChild extends Children

sealed trait Diet
case object GlutenFree  extends Diet
case object Omnivore    extends Diet
case object Pescatarian extends Diet
case object Vegetarian  extends Diet
case object Vegan       extends Diet
case object OtherDiet   extends Diet

sealed trait Religion
case object Agnostic      extends Religion
case object Athiest       extends Religion
case object Buddhist      extends Religion
case object Christian     extends Religion
case object Catholic      extends Religion
case object Mormon        extends Religion
case object Muslim        extends Religion
case object OtherReligion extends Religion

sealed trait ExactOrArea
case object ExactLocation extends ExactOrArea
case object Area          extends ExactOrArea

class HostPreference(s: Smoking, p: Pets, c: Children, d: Diet, r: Religion, e: ExactOrArea) {
  val smoking = s
  val pets = p
  val children = c
  val diet = d
  val religion = r
  val eoa = e
}
