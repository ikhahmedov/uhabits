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

package org.isoron.uhabits.tasks;

import android.support.annotation.Nullable;

import org.isoron.uhabits.utils.DatabaseUtils;
import org.isoron.uhabits.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class ExportDBTask extends BaseTask
{
    public interface Listener
    {
        void onExportDBFinished(@Nullable String filename);
    }

    private ProgressBar progressBar;
    private String filename;
    private Listener listener;

    public ExportDBTask(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        if(progressBar != null) progressBar.show();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        if(listener != null) listener.onExportDBFinished(filename);
        if(progressBar != null) progressBar.hide();
        super.onPostExecute(null);
    }

    @Override
    protected void doInBackground()
    {
        filename = null;

        try
        {
            File dir = FileUtils.getFilesDir("Backups");
            if(dir == null) return;

            filename = DatabaseUtils.saveDatabaseCopy(dir);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
