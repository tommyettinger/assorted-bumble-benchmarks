
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
 * Gnome32RandomIntBench score: 329539520.000000 (329.5M 1961.3%)
 *                   uncertainty:   1.0%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Gnome32RandomIntBench score: 399780544.000000 (399.8M 1980.6%)
 *                   uncertainty:   0.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Gnome32RandomIntBench score: 444140224.000000 (444.1M 1991.2%)
 *                   uncertainty:   0.7%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Gnome32RandomIntBench score: 360988384.000000 (361.0M 1970.4%)
 *                   uncertainty:   0.5%
 */
public final class Gnome32RandomIntBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Gnome32Random rng = new Gnome32Random(0x12345678);
		int sum = 0;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextInt();
		return numIterations;
	}
}
