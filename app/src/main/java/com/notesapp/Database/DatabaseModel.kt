package com.notesapp.Database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotesModel(var userId: Int, var notesId: Int, var title: String?, var body: String?,
                 var favorite: Boolean, var completed: Boolean): Parcelable