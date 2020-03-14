package com.stripe.rainier.core

import com.stripe.rainier.compute._
import com.stripe.rainier.sampler._

trait SBCModel[T] {
  def sbc: SBC[T]
  val warmupIterations: Int = 10000
  val syntheticSamples: Int = 1000
  val nSamples: Int = 10
  def sampler(iterations: Int) = EHMC(warmupIterations, iterations)
  def main(args: Array[String]): Unit = {
    implicit val rng: RNG = ScalaRNG(1528673302081L)
    sbc.animate(syntheticSamples)(sampler)
    println(s"\nnew goldset:")
    println(s"$samples")
    println(s"\ngoldset true value: $trueValue")
    println(
      s"If this run looks good, please update the goldset in your SBCModel")
  }
  val (samples, trueValue) = {
    implicit val rng: RNG = ScalaRNG(1528673302081L)
    val (values, trueValue) = sbc.synthesize(syntheticSamples)
    val (model, real) = sbc.fit(values)
    val samples =
      model.sample(sampler(goldset.size), 1).predict(real)
    (samples, trueValue)
  }

  def goldset: List[Double]
  val description: String
}

/** Continuous **/
object SBCUniformNormal extends SBCModel[Double] {
  def sbc = SBC(Uniform(0, 1))((x: Real) => Normal(x, 1))
  def goldset =
    List(0.41017188683294314, 0.3616793215374072, 0.4588864663275664,
      0.3585934514598037, 0.3661954526232371, 0.3661954526232371,
      0.3661954526232371, 0.3661954526232371, 0.3661954526232371,
      0.422323689478411, 0.422323689478411, 0.43008442566497873,
      0.464762503135008, 0.464762503135008, 0.35272642970892654,
      0.37651429316785406, 0.383328568312589, 0.40833318579808103,
      0.3692362391280442, 0.3692362391280442, 0.41316724405169086,
      0.41316724405169086, 0.41316724405169086, 0.37981446026906956,
      0.37981446026906956, 0.37981446026906956, 0.37981446026906956,
      0.37981446026906956, 0.37088671931027306, 0.39414790170881636)

  val description = "Normal(x, 1) with Uniform(0, 1) prior"
}

object SBCLogNormal extends SBCModel[Double] {
  def sbc =
    SBC(LogNormal(0, 1))((x: Real) => LogNormal(x, x))
  def goldset =
    List(0.7668909203537352, 0.7768802570979615, 0.7527219346129361,
      0.7406724086261876, 0.7467454362335779, 0.8055482445486698,
      0.7942454822485939, 0.7539569327692565, 0.7954305745263369,
      0.7572980318104288, 0.7633495330295792, 0.7737417648064088,
      0.8035491206998703, 0.7708345436982886, 0.7842607894556024,
      0.7632405017699714, 0.7611048031053049, 0.7933865124516037,
      0.7912075680297423, 0.7626373061577915, 0.7947147512402191,
      0.7948050588215506, 0.7533436931423075, 0.7497894837102681,
      0.7497894837102681, 0.7982653533017361, 0.7981125347148085,
      0.7499461615447484, 0.7523011331739383, 0.7555828028265458)

  val description = "LogNormal(x, x) with LogNormal(0, 1) prior"
}

object SBCExponential extends SBCModel[Double] {
  def sbc =
    SBC(LogNormal(0, 1))((x: Real) => Exponential(x))
  def goldset =
    List(0.8040774834741757, 0.7697608213467314, 0.8057971067481746,
      0.7685603769045286, 0.7685603769045286, 0.8072119230703328,
      0.7724201299195624, 0.7905807248269311, 0.7905807248269311,
      0.794262387562032, 0.790920664457526, 0.790920664457526,
      0.7898368471823354, 0.7898368471823354, 0.8060486679005427,
      0.8051253805481703, 0.782890410306679, 0.8115738528019246,
      0.8106461780568365, 0.7711436215106905, 0.8078293568260968)
  val description = "Exponential(x) with LogNormal(0, 1) prior"
}

object SBCLaplace extends SBCModel[Double] {
  def sbc = SBC(LogNormal(0, 1))((x: Real) => Laplace(x, x))
  def goldset =
    List(0.7642066336273645, 0.7642066336273645, 0.7642066336273645,
      0.7384590847168169, 0.7384590847168169, 0.7970881160074392,
      0.7970881160074392, 0.7484295728612199, 0.7780365322888609,
      0.7510726879718242, 0.7510726879718242, 0.7813430068522235,
      0.7813430068522235, 0.758882010251168, 0.7773469952874031,
      0.7773469952874031, 0.7773469952874031, 0.7773469952874031,
      0.7773469952874031, 0.7791986413736735, 0.7567447891995387)

  val description = "Laplace(x, x) with LogNormal(0, 1) prior"
}

object SBCGamma extends SBCModel[Double] {
  def sbc = SBC(LogNormal(0, 1))((x: Real) => Gamma(x, x))
  def goldset =
    List(0.7698406376565916, 0.7605749266362993, 0.7504906816469634,
      0.7504906816469634, 0.7520174166723872, 0.7510455646366141,
      0.7802499951158273, 0.7802499951158273, 0.7590173908499321,
      0.7700662697337262, 0.7700662697337262, 0.7700662697337262,
      0.7459043436966699, 0.7459043436966699, 0.7692996678948734,
      0.7461753567598703, 0.7467275902812742, 0.7467275902812742,
      0.7707184616358422, 0.7627155831357896, 0.7627155831357896)
  val description = "Gamma(x, x) with LogNormal(0, 1) prior"
}

/** Discrete **/
object SBCBernoulli extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 1))((x: Real) => Bernoulli(x))
  def goldset =
    List(0.4401823366195266, 0.4742522486126828, 0.4373729473811324,
      0.47441289447498286, 0.4658708136979508, 0.43602330092016384,
      0.4805380792126082, 0.4805380792126082, 0.4805380792126082,
      0.455223465357471, 0.4680953977370082, 0.4680953977370082,
      0.44582902588657464, 0.4686094868453493, 0.441059277521413,
      0.46936149946087025, 0.47029549724483066, 0.4340822201219064,
      0.4871677002420421, 0.4783453372556803, 0.43741862589381436,
      0.48175894481568465, 0.4437304412394825, 0.47771215287374474,
      0.4782111065845889, 0.4754800100838506, 0.4432920760187733,
      0.47579749011127265, 0.4373688446837599, 0.44519854421848387)

  val description = "Bernoulli(x) with Uniform(0, 1) prior"
}

object SBCBinomial extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 1))((x: Real) => Binomial(x, 10))
  def goldset =
    List(0.46098361528742743, 0.45599881729187425, 0.45599881729187425,
      0.45808371243444473, 0.45527815312213127, 0.4588281916830044,
      0.46150578103353235, 0.45430562146315506, 0.45228274603774743,
      0.4629107219820057, 0.4507354984099119, 0.4638935682506889,
      0.4543057139140435, 0.459160644627697, 0.459160644627697,
      0.4561571589935201, 0.45991282716153287, 0.4562698874069948,
      0.4618051974326652, 0.45308790719474823, 0.4599763676431776,
      0.4552963411372326, 0.45731106556786344, 0.45731106556786344,
      0.4580279018806328, 0.4584269648938272, 0.4562724964411611,
      0.45576930536528515, 0.46072406212529987, 0.45540716627060807)

  val description = "Binomial(x, 10) with Uniform(0, 1) prior"
}

object SBCGeometric extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 1))((x: Real) => Geometric(x))
  def goldset =
    List(0.4287105349438442, 0.4287105349438442, 0.41021786928520143,
      0.41021786928520143, 0.4457684544863336, 0.4457684544863336,
      0.41473827075503983, 0.43569867687309644, 0.41798837446375925,
      0.41798837446375925, 0.43640731763910384, 0.43640731763910384,
      0.42249904576797376, 0.43373145821055636, 0.43373145821055636,
      0.43373145821055636, 0.43373145821055636, 0.43373145821055636,
      0.4364213989386786, 0.4216010651625925, 0.4216010651625925,
      0.41813290104070167, 0.4400805356335908, 0.4400805356335908,
      0.4163576991493826, 0.4163576991493826, 0.4239788600387847,
      0.4262983521091081, 0.4262983521091081, 0.4262983521091081)
  val description = "Geometric(x) with Uniform(0, 1) prior"
}

object SBCGeometricZeroInflated extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 1))((x: Real) => Geometric(.3).zeroInflated(x))
  def goldset =
    List(0.3682741265983013, 0.3852529612682177, 0.3604238333388064,
      0.386596856795002, 0.3555033555350904, 0.39217253775032623,
      0.39217253775032623, 0.3550136947273093, 0.38182164324962603,
      0.38182164324962603, 0.36722421685006434, 0.3902898312064711,
      0.3531099114009195, 0.3942443064061138, 0.35565699293349323,
      0.38180128466000113, 0.38180128466000113, 0.37379355799257075,
      0.3692587358496806, 0.3588953235783851, 0.3931958168156864,
      0.3585549007172857, 0.39441682029588876, 0.353399981549561,
      0.38860439210841013, 0.35795264331182514, 0.38336146622540296,
      0.35691143054863744, 0.35691143054863744, 0.39069497264393727)
  val description = "Geometric(.3).zeroInflated(x) with Uniform(0, 1) prior"
}

object SBCNegativeBinomial extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 1))((x: Real) => NegativeBinomial(x, 10))
  def goldset =
    List(0.4553926770068776, 0.46209605583937796, 0.46209605583937796,
      0.45368544386574533, 0.4573797790430212, 0.4573797790430212,
      0.45320071372096127, 0.45320071372096127, 0.4585116596148313,
      0.4585116596148313, 0.45432788580046957, 0.45432788580046957,
      0.45536763126593194, 0.46328600301357886, 0.4523801519552559,
      0.44843331789401597, 0.45967056772799697, 0.45967056772799697,
      0.45967056772799697, 0.45967056772799697, 0.45757312703780834,
      0.45757312703780834, 0.45757312703780834, 0.45757312703780834,
      0.4685970355410904, 0.45086401829717165, 0.45086401829717165,
      0.4511952976711324, 0.46217710254268235, 0.46217710254268235)
  val description = "NegativeBinomial(x, 10) with Uniform(0, 1) prior"
}

object SBCBinomialPoissonApproximation extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0, 0.04))((x: Real) => Binomial(x, 200))
  def goldset =
    List(0.01756028908065821, 0.01759587121764895, 0.01733238808851419,
      0.017524678172740935, 0.017579196408050207, 0.017949056269338674,
      0.017093045484406207, 0.01719754091282047, 0.017965687038437986,
      0.017965687038437986, 0.01753970395926845, 0.017587468400266624,
      0.017647816561886992, 0.01831863784685146, 0.016731214508834972,
      0.018357875700939937, 0.017645698168365552, 0.017501183912530513,
      0.017671373369488007, 0.017671373369488007, 0.017837295510069535,
      0.017131071684057798, 0.016827484246414875, 0.01832276177197235,
      0.01832276177197235, 0.01705825386430419, 0.01761570647504787,
      0.017608008688230065, 0.01751885161843742, 0.017510386454633414)

  val description =
    "Poisson approximation to Binomial: Binomial(x, 200) with Uniform(0, 0.04) prior"
}

object SBCBinomialNormalApproximation extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0.4, 0.6))((x: Real) => Binomial(x, 300))
  def goldset =
    List(0.3849093699203164, 0.3822629591199987, 0.38374303903059914,
      0.3832324864849272, 0.38389219985327067, 0.3835866724851299,
      0.3830818707173923, 0.3835660923949862, 0.3841609845229018,
      0.3820783526940713, 0.3849827665275495, 0.38236510331364865,
      0.3838715727269887, 0.3840183371678221, 0.3828960655858176,
      0.3841785878664216, 0.3827156431825414, 0.38488715647596444,
      0.38157319616823965, 0.38511364643858215, 0.3819850513881394,
      0.3857373545069211, 0.3818792213826954, 0.38587080685826664,
      0.3813170524170525, 0.38588405883758004, 0.3809126103183984,
      0.38614580147352096, 0.38091354056091625, 0.3841577712144981)
  val description =
    "Normal approximation to Binomial: Binomial(x, 200) with Uniform(0.4, 0.6) prior"

}

object SBCLargePoisson extends SBCModel[Long] {
  def sbc =
    SBC(Uniform(0.8, 1))((x: Real) => Poisson(x * 1000))
  def goldset =
    List(0.8896349481612058, 0.8896349481612058, 0.8885818635805025,
      0.8877725908728087, 0.8896466850658817, 0.8896466850658817,
      0.8865420383245867, 0.8890007745543766, 0.8890007745543766,
      0.8890007745543766, 0.889214282921085, 0.889214282921085,
      0.889214282921085, 0.8884035373062821, 0.8884035373062821,
      0.8884035373062821, 0.8901419470474745, 0.8893965196352073,
      0.8904689666375939, 0.887781151309036, 0.887781151309036,
      0.8900459067906088, 0.8877582818307215, 0.890099748824956,
      0.8895989110588538, 0.8880595333887795, 0.8880595333887795,
      0.8894558924425451, 0.8879694546000153, 0.8891662635397245)

  val description =
    "Poisson(x*1000) with Uniform(0.8, 1) prior"
}

object SBCBVNormal extends SBCModel[(Double, Double)] {
  def sbc =
    SBC(Uniform(-1, 1))((x: Real) => BivariateNormal.standard(x))
  def goldset =
    List(-0.039037398757803876, -0.028159299731772003, -0.03128272168218793,
      -0.056656558112087474, -0.028754401364823656, -0.06820662706056546,
      0.021743734470394793, -0.020717133584187475, -0.057875449264825685,
      -0.057875449264825685, -0.0857437568856051, -0.04851880628846916,
      -0.04851880628846916, -0.05201990514582655, -0.09416980170894851,
      -0.09416980170894851, -0.11320814387358125, 0.013104139790693914,
      0.007666364697863859, 0.007666364697863859, 0.007666364697863859,
      -0.04742299135608241, -0.03119490789271251, -0.04277495689113042,
      -0.004553093443717349, -0.04038994337408042, -0.056092930808150476,
      -0.04581822869590635, -0.037228099676384074, -0.022810515151003408)

  val description =
    "BivariateNormal with Uniform(-1, 1) prior"
}
