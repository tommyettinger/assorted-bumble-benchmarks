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
// I think this is an improvement.
/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 114967240.000000 (115.0M 1856.0%)
 *                          uncertainty:   1.5%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 119983336.000000 (120.0M 1860.3%)
 *                          uncertainty:   1.1%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 109236040.000000 (109.2M 1850.9%)
 *                          uncertainty:   0.6%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianLinnormalV2Bench score: 124712776.000000 (124.7M 1864.2%)
 *                          uncertainty:   1.7%
 */
// rearranged some code just a little.
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 65672448.000000 (65.67M 1800.0%)
 *                          uncertainty:   0.6%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 113749704.000000 (113.7M 1855.0%)
 *                          uncertainty:   0.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 93526344.000000 (93.53M 1835.4%)
 *                          uncertainty:   0.7%
 * <br>
 * GraalVM Java 22:
 * <br>
 *  FiveGaussianLinnormalV2Bench score: 132524176.000000 (132.5M 1870.2%)
 *                          uncertainty:   0.7%
 */
/* * // first try of V2
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 65466664.000000 (65.47M 1799.7%)
 *                          uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 107739728.000000 (107.7M 1849.5%)
 *                          uncertainty:   0.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalV2Bench score: 102382656.000000 (102.4M 1844.4%)
 *                          uncertainty:   0.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianLinnormalV2Bench score: 131421896.000000 (131.4M 1869.4%)
 *                          uncertainty:   1.0%
 */
public final class FiveGaussianLinnormalV2Bench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		GoldenQuasiRandom rng = new GoldenQuasiRandom(0x12345678L);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Linnormal.normalV2(rng.nextLong());
			sum += Linnormal.normalV2(rng.nextLong());
			sum += Linnormal.normalV2(rng.nextLong());
			sum += Linnormal.normalV2(rng.nextLong());
			sum += Linnormal.normalV2(rng.nextLong());
        }
		return numIterations;
	}
}

// TEMPLATE

/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 22:
 * <br>
 *
 */
// OLD, uses PouchRandom
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianLinnormalBench score: 107790920.000000 (107.8M 1849.6%)
 *                        uncertainty:   0.5%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalBench score: 58685964.000000 (58.69M 1788.8%)
 *                        uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalBench score: 40428100.000000 (40.43M 1751.5%)
 *                        uncertainty:   0.4%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianLinnormalBench score: 103583752.000000 (103.6M 1845.6%)
 *                        uncertainty:   0.9%
 */

// OLD, has problems in the trail of the distribution
/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianLinnormalBench score: 88797264.000000 (88.80M 1830.2%)
 *                      uncertainty:   0.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalBench score: 87081856.000000 (87.08M 1828.2%)
 *                      uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalBench score: 103184184.000000 (103.2M 1845.2%)
 *                      uncertainty:   1.0%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianLinnormalBench score: 106505464.000000 (106.5M 1848.4%)
 *                      uncertainty:   0.8
 */
