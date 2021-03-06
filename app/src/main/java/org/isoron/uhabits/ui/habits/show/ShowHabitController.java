/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.isoron.uhabits.ui.habits.show;

import android.support.annotation.*;

import org.isoron.uhabits.*;
import org.isoron.uhabits.commands.*;
import org.isoron.uhabits.models.*;
import org.isoron.uhabits.tasks.*;
import org.isoron.uhabits.ui.habits.edit.*;

import javax.inject.*;

public class ShowHabitController implements ShowHabitRootView.Controller,
                                            HistoryEditorDialog.Controller
{
    @NonNull
    private final ShowHabitScreen screen;

    @NonNull
    private final Habit habit;

    @Inject
    CommandRunner commandRunner;

    public ShowHabitController(@NonNull ShowHabitScreen screen,
                               @NonNull Habit habit)
    {
        HabitsApplication.getComponent().inject(this);
        this.screen = screen;
        this.habit = habit;
    }

    @Override
    public void onToolbarChanged()
    {
        screen.invalidateToolbar();
    }

    @Override
    public void onEditHistoryButtonClick()
    {
        screen.showEditHistoryDialog(this);
    }

    @Override
    public void onToggleCheckmark(long timestamp)
    {
        new SimpleTask(() -> {
            ToggleRepetitionCommand command;
            command = new ToggleRepetitionCommand(habit, timestamp);
            commandRunner.execute(command, null);
        }).execute();
    }
}
