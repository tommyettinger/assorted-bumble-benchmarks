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

import com.github.tommyettinger.random.GoldenQuasiRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianBoxMullerBench score: 5362142.000000 (5.362M 1549.5%)
 *                        uncertainty:   0.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianBoxMullerBench score: 6590391.500000 (6.590M 1570.1%)
 *                        uncertainty:   1.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianBoxMullerBench score: 6598713.500000 (6.599M 1570.2%)
 *                        uncertainty:   2.7%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianBoxMullerBench score: 14921352.000000 (14.92M 1651.8%)
 *                        uncertainty:   0.9%
 */
// OLD, uses PouchRandom
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianBoxMullerBench score: 3463358.500000 (3.463M 1505.8%)
 *                        uncertainty:   0.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianBoxMullerBench score: 8285829.000000 (8.286M 1593.0%)
 *                        uncertainty:   0.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianBoxMullerBench score: 12780422.000000 (12.78M 1636.3%)
 *                        uncertainty:   0.5%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianBoxMullerBench score: 14331013.000000 (14.33M 1647.8%)
 *                        uncertainty:   0.9
 */
public final class FiveGaussianBoxMullerBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		GoldenQuasiRandom rng = new GoldenQuasiRandom(0x12345678L);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Math.sqrt(-2 * Math.log(1 - rng.nextDouble())) * Math.cos(2 * Math.PI * rng.nextDouble());
			sum += Math.sqrt(-2 * Math.log(1 - rng.nextDouble())) * Math.cos(2 * Math.PI * rng.nextDouble());
			sum += Math.sqrt(-2 * Math.log(1 - rng.nextDouble())) * Math.cos(2 * Math.PI * rng.nextDouble());
			sum += Math.sqrt(-2 * Math.log(1 - rng.nextDouble())) * Math.cos(2 * Math.PI * rng.nextDouble());
			sum += Math.sqrt(-2 * Math.log(1 - rng.nextDouble())) * Math.cos(2 * Math.PI * rng.nextDouble());}
		return numIterations;
	}
}
