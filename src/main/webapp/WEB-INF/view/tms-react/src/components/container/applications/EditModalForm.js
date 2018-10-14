import FormModal from '../../ui/generic/ModalForm'
import { hideEditApplicationForm, completeEditingApplication } from '../../../actions/applicationActions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    isVisible: state.application.editApplication.editApplicationFormVisibility
})

const mapDispatchToProps = dispatch => ({
    onCloseModal() {
        dispatch(hideEditApplicationForm())
        dispatch(completeEditingApplication())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(FormModal)