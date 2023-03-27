package org.opentripplanner.ext.fares.model;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import org.opentripplanner.framework.tostring.ToStringBuilder;
import org.opentripplanner.transit.model.basic.Money;
import org.opentripplanner.transit.model.framework.FeedScopedId;

/**
 * A ticket that a user can purchase to travel.
 * <p>
 * It may be valid for the entirety of an itinerary or just for some of its legs.
 *
 * @param id       Identity for the
 * @param name     Human-readable name of the product
 * @param amount   Price
 * @param duration Maximum duration of the product, if null then unlimited duration
 * @param category Rider category, for example seniors or students
 * @param medium   Medium to "hold" the fare, like "cash", "HSL app" or
 */
public record FareProduct(
  FeedScopedId id,
  String name,
  Money amount,
  @Nullable Duration duration,
  @Nullable RiderCategory category,
  @Nullable FareMedium medium
) {
  public FareProduct {
    Objects.requireNonNull(id);
    Objects.requireNonNull(name);
    Objects.requireNonNull(amount);
  }

  public boolean coversDuration(Duration journeyDuration) {
    return Objects.nonNull(duration) && duration.toSeconds() > journeyDuration.toSeconds();
  }

  @Override
  public String toString() {
    var builder = ToStringBuilder
      .of(FareProduct.class)
      .addStr("id", id.toString())
      .addObj("amount", amount);
    if (duration != null) {
      builder.addDuration("duration", duration);
    }
    if (category != null) {
      builder.addObj("category", category);
    }
    if (medium != null) {
      builder.addObj("medium", medium);
    }

    return builder.toString();
  }

  /**
   * Computes a unique ID for this product based on its id and properties.
   * <p>
   * This ID can then be used as a deduplication id to identify the fare product across legs.
   * <p>
   * For example, there can be two legs which have the fare products day pass and single ticket
   * each. However, the day passes have the same instance id meaning it's a single fare product for
   * both legs. The two single tickets have different instance ids and which means that the
   * passenger has to buy two of them.
   */
  public String uniqueInstanceId(ZonedDateTime startTime) {
    var input = toString() + startTime.toEpochSecond();
    return UUID.nameUUIDFromBytes(input.getBytes(StandardCharsets.UTF_8)).toString();
  }
}
