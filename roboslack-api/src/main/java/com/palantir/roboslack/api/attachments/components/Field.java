/*
 * Copyright 2017 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.roboslack.api.attachments.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.palantir.roboslack.utils.MorePreconditions;
import org.immutables.value.Value;

/**
 * Represents a {@code Field} as it is displayed within a table for a Slack message attachment.
 *
 * @see <a href="https://api.slack.com/docs/message-attachments">Slack Message Attachments</a>
 * @since 0.1.0
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableField.class)
@JsonSerialize(as = ImmutableField.class)
public abstract class Field {

    private static final String TITLE_FIELD = "title";
    private static final String VALUE_FIELD = "text";
    private static final String SHORT_FIELD = "short";

    public static Builder builder() {
        return ImmutableField.builder();
    }

    public static Field of(String title, String value) {
        return builder().title(title).value(value).build();
    }

    /**
     * Indicator for whether the {@link Field#value()} is short enough to be displayed side-by-side with other values.
     * Usually, anything longer than forty characters would be considered long (isShort = false).
     *
     * @return whether or not the text is considered short
     */
    @Value.Default
    @JsonProperty(SHORT_FIELD)
    public boolean isShort() {
        return true;
    }

    @Value.Check
    protected final void check() {
        MorePreconditions.checkDoesNotContainMarkdown(TITLE_FIELD, title());
        MorePreconditions.checkDoesNotContainMarkdown(VALUE_FIELD, value());
    }

    public interface Builder {
        Builder title(String title);
        Builder value(String value);
        Builder isShort(boolean isShort);
        Field build();
    }

    /**
     * The bold heading above the {@link Field#value()} text.
     *
     * @return the title
     */
    @JsonProperty(TITLE_FIELD)
    public abstract String title();

    /**
     * The text content of this {@link Field}.
     *
     * @return the text content
     */
    @JsonProperty(VALUE_FIELD)
    public abstract String value();

}
