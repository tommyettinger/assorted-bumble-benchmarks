/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.examples;

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * MathCbrtBench score: 21551406.000000 (21.55M 1688.6%)
 *         uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * MathCbrtBench score: 171566144.000000 (171.6M 1896.0%)
 *         uncertainty:   0.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * MathCbrtBench score: 178729584.000000 (178.7M 1900.1%)
 *         uncertainty:   0.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * MathCbrtBench score: 170263792.000000 (170.3M 1895.3%)
 *         uncertainty:   0.4%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * MathCbrtBench score: 168600896.000000 (168.6M 1894.3%)
 *         uncertainty:   0.4%
 */
public final class MathCbrtBench extends MicroBench {
	 protected long doBatch (long numIterations) throws InterruptedException {
		  double sum = 0.01;
		  final double shrink = 16180.339887498949 / numIterations;
		  for (long i = 0; i < numIterations; i++)
			  sum += Math.cbrt(i + shrink);
		  return numIterations;
	 }
}

/* OLD
 * Windows 10, 10th gen i7 mobile hexacore at 2.6 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * MathCbrtBench score: 17421624.000000 (17.42M 1667.3%)
 *          uncertainty:   0.3%
 * <br>
 * OpenJ9 Java 15:
 * <br>
 * MathCbrtBench score: 27825858.000000 (27.83M 1714.1%)
 *          uncertainty:   1.6%
 * <br>
 * HotSpot Java 16:
 * <br>
 * MathCbrtBench score: 29086090.000000 (29.09M 1718.6%)
 *          uncertainty:   0.1%
 */
