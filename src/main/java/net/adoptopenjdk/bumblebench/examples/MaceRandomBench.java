
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

import com.github.tommyettinger.random.MaceRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
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
 * GraalVM Java 24:
 * <br>
 *
 */
public final class MaceRandomBench extends MicroBench {

	private long sum = 0L;
	protected long doBatch(long numIterations) throws InterruptedException {
		MaceRandom rng = new MaceRandom(0x12345678);
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}

	/**
	 * Optionally implemented by subclasses and called at the end of a run to verify
	 * whether the run was correct or not. Defaults to true. If false, an ERROR message
	 * is printed instead of the final score. Implementing methods may output their own
	 * error message(s) as well.
	 */
	@Override
	protected boolean verify() {
		System.out.println("Final sum was: ");
		System.out.println(sum);
		System.out.println();
		return super.verify();
	}
}
