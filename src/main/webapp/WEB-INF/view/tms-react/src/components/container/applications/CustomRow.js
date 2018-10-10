import CustomRow from '../../ui/generic/CustomRow'
import { selectApplicationEntry, unselectApplicationEntry } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntries: state.application.selectedApplicationEntries
})

const mapDispatchToProps = (dispatch, ownProps) => ({
    onSelectEntry(e, data) {
        console.log(ownProps)
        let isEntrySelected = data.checked
        let application = ownProps.row
        if (isEntrySelected) {
            dispatch(selectApplicationEntry(application))
        } else {
            dispatch(unselectApplicationEntry(application))
        }
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(CustomRow)